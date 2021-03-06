package sk.upjs.ics.bookwarehouse;

import sk.upjs.ics.bookwarehouse.business.BookEditManager;
import sk.upjs.ics.bookwarehouse.business.DefaultBookEditManager;
import sk.upjs.ics.bookwarehouse.business.LostPasswordManager;
import sk.upjs.ics.bookwarehouse.business.PasswordManager;
import sk.upjs.ics.bookwarehouse.business.SHA256PasswordManager;
import sk.upjs.ics.bookwarehouse.business.BookLendingManager;
import sk.upjs.ics.bookwarehouse.business.DefaultBookLendingManager;
import sk.upjs.ics.bookwarehouse.business.DefaultRegistrationManager;

public enum ManagerFactory {
    INSTANCE;

    public PasswordManager getPasswordManager() {
        return new SHA256PasswordManager();
    }

    public BookLendingManager getBookLendingManager() {
        return new DefaultBookLendingManager();
    }

    public BookEditManager getBookEditManager() {
        return new DefaultBookEditManager();
    }

    public LostPasswordManager getLostPasswordManager() {
        return new LostPasswordManager();
    }

}
