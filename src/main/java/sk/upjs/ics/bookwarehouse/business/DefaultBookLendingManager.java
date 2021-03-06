package sk.upjs.ics.bookwarehouse.business;

import com.sun.org.apache.bcel.internal.generic.RETURN;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import sk.upjs.ics.bookwarehouse.Admin;
import sk.upjs.ics.bookwarehouse.Book;
import sk.upjs.ics.bookwarehouse.BookLending;
import sk.upjs.ics.bookwarehouse.DaoFactory;
import sk.upjs.ics.bookwarehouse.LostBook;
import sk.upjs.ics.bookwarehouse.Teacher;
import sk.upjs.ics.bookwarehouse.storage.BookLendingDao;

public class DefaultBookLendingManager implements BookLendingManager {

    private BookLendingDao bookLendingDao = DaoFactory.INSTANCE.getBookLendingDao();

    @Override
    public void deleteByTeacher(Teacher teacher) {
        Admin admin = DaoFactory.INSTANCE.getAdminDao().getAll().get(0);
        List<BookLending> bookLendings = bookLendingDao.getAll();
        for (BookLending bookLending : bookLendings) {
            boolean areThereLostBooks = false;
            if (bookLending.getTeacher().getId() == teacher.getId()) {
                if (bookLending.isApproved()) {
                    if (bookLending.getLost() > 0) {
                        LostBook lostBook = new LostBook(bookLending, admin, "strata");
                        DaoFactory.INSTANCE.getLostBookDao().save(lostBook);
                    }
                    Book book = bookLending.getBook();
                    book.setNumberOfUsed(book.getNumberOfUsed() - bookLending.getReturned() - bookLending.getLost());
                    book.setNumberInStock(book.getNumberInStock() + bookLending.getReturned());
                    DaoFactory.INSTANCE.getBookDao().save(book);
                } else {
                    Book book = bookLending.getBook();
                    book.setNumberOfUsed(book.getNumberOfUsed() - bookLending.getLended());
                    book.setNumberInStock(book.getNumberInStock() + bookLending.getLended());
                    DaoFactory.INSTANCE.getBookDao().save(book);
                }
                bookLendingDao.deleteById(bookLending.getId());
            }
        }
    }

    @Override
    public void deleteAllForThisYear() {
        deleteAllForYear(LocalDateTime.now().getYear());
    }

    @Override
    public void deleteAllForYear(int year) {
        List<BookLending> bookLendings = bookLendingDao.getAll();
        for (BookLending bookLending : bookLendings) {
            if (bookLending.getYearOfReturn() == year) {
                bookLendingDao.deleteById(bookLending.getId());
            }
        }
    }

    @Override
    public List<BookLending> myBookLendings(Teacher teacher) {
        List<BookLending> list = bookLendingDao.getAll();
        List<BookLending> myList = new ArrayList<>();
        for (BookLending bookLending : list) {
            if (bookLending.getTeacher().getId().equals(teacher.getId())) {
                myList.add(bookLending);
            }
        }
        return myList;
    }

}
