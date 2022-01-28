package ru.filit.mdma.dm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.filit.mdma.dm.exception.WrongDataException;
import ru.filit.mdma.dm.model.Account;
import ru.filit.mdma.dm.model.AccountBalance;
import ru.filit.mdma.dm.model.ClientLevel;
import ru.filit.mdma.dm.model.Operation;
import ru.filit.mdma.dm.web.dto.AccessDto;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountService {


    private final URL accountsFileUrl ;

    private final URL balancesFileUrl;

    public AccountService(@Value("${datafiles.accounts}") String accountsFileName, @Value("${datafiles.balances}") String balancesFileName){
        this.accountsFileUrl = getClass().getClassLoader().getResource(accountsFileName);
        this.balancesFileUrl = getClass().getClassLoader().getResource(balancesFileName);
    }

    @Autowired
    private YamlService yamlService;

    @Autowired
    private OperationService operationService;

    public List<Account> getAccounts(String clientId) throws IOException {
        List<Account> accounts = yamlService.readYaml(accountsFileUrl, Account.class);
        return accounts.stream().filter(a->a.getClientId().equals(clientId)).collect(Collectors.toList());
    }

    public Account getAccount(String accountNumber) throws IOException, WrongDataException {
        List<Account> accounts = yamlService.readYaml(accountsFileUrl, Account.class);
        return accounts.stream().filter(a->a.getNumber().equals(accountNumber)).findFirst().orElseThrow(()->new WrongDataException("No such account"));
    }

    public BigDecimal getBalance(String accountNumber, Date date) throws IOException, WrongDataException {
        List<AccountBalance> balances = yamlService.readYaml(balancesFileUrl, AccountBalance.class);
        AccountBalance lastBalance =balances.stream().filter(b-> b.getAccountNumber().equals(accountNumber)).
                max(Comparator.comparing(AccountBalance::getBalanceDate)).orElseThrow(()->new WrongDataException("Account number error"));
        List<Operation> operations =operationService.getOperations(accountNumber, new Date(lastBalance.getBalanceDate()*1000L), date);
        BigDecimal currentBalance=lastBalance.getAmount();
        for(Operation operation: operations){
            if(operation.getType()== Operation.TypeEnum.RECEIPT){
                currentBalance=currentBalance.add(operation.getAmount());
            }else if (operation.getType()== Operation.TypeEnum.EXPENSE){
                currentBalance=currentBalance.subtract(operation.getAmount());
            }
        }
        return currentBalance;
    }

    public BigDecimal getCurrentBalance(String accountNumber) throws WrongDataException, IOException {
        return  getBalance(accountNumber, new Date());
    }

    public BigDecimal getSVO(String accountNumber) throws WrongDataException, IOException {
        Account ac= getAccount(accountNumber);
        Calendar c=Calendar.getInstance();
        c.set(Calendar.HOUR, 23);
        c.set(Calendar.MINUTE, 59);
        int n=30;
        if(c.getTimeInMillis()- ac.getOpenDate()*1000L< 1000L *60*60*24*30){
            n = (int) ((c.getTimeInMillis()- ac.getOpenDate()*1000L)/(1000L *60*60*24*30));
        }
        BigDecimal svo=new BigDecimal(0);
        for (int i = 0; i < n; i++) {
            svo=svo.add(getBalance(accountNumber, new Date(c.getTimeInMillis()-1000L *60*60*24*30*n)));
        }
        svo=svo.divide(new BigDecimal(n));
        return svo;
    }

    public BigDecimal getOverdraft(String accountNumber) throws WrongDataException, IOException {
        Account account=getAccount(accountNumber);
        if(account.getType()!=Account.TypeEnum.OVERDRAFT) throw new WrongDataException("This account is not overdraft");
        Date openDate =new Date(account.getOpenDate()*1000L);
        Calendar c=Calendar.getInstance();
        c.setTime(openDate);
        c.set(Calendar.HOUR, 23);
        c.set(Calendar.MINUTE, 59);
        BigDecimal percents=new BigDecimal(0);
        int deferment= account.getDeferment();
        while (c.getTimeInMillis()<=new Date().getTime()){
            if(c.get(Calendar.DAY_OF_WEEK)!= Calendar.SATURDAY && c.get(Calendar.DAY_OF_WEEK)!= Calendar.SUNDAY){
                BigDecimal currentBalance=getBalance(accountNumber, c.getTime());
                if(currentBalance.compareTo(new BigDecimal(0)) ==-1){
                    if(deferment==0) {
                        percents = percents.add(currentBalance.abs().multiply(new BigDecimal("0.0007")).setScale(2, RoundingMode.HALF_UP));
                    }else{
                        deferment--;
                    }
                }else{
                    deferment= account.getDeferment();
                }
            }
            c.setTimeInMillis(c.getTimeInMillis()+1000L*60*60*24);
        }
        return percents;
    }

}
