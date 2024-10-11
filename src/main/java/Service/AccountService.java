package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public Account addAccount(Account account) {
        if(accountDAO.findExistingAccountbyName(account.getUsername()) == null && 
        account.getPassword().length() > 4 &&
        account.getUsername().length() > 0){
            return accountDAO.addAccount(account);
        }
        return null;
    }

    public Account login(Account account){
        Account loginAccount = accountDAO.findExistingAccountbyName(account.getUsername());
        if(accountDAO.findExistingAccountbyName(account.getUsername()) != null &&
        loginAccount.getUsername().equals(account.getUsername()) && 
        loginAccount.getPassword().equals(account.getPassword())){
            return loginAccount;
        }
        return null;
    }
}
