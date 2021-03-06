package sk.upjs.ics.bookwarehouse.business;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.xml.bind.DatatypeConverter;
import sk.upjs.ics.bookwarehouse.Admin;
import sk.upjs.ics.bookwarehouse.DaoFactory;
import sk.upjs.ics.bookwarehouse.ManagerFactory;
import sk.upjs.ics.bookwarehouse.SuperAdmin;
import sk.upjs.ics.bookwarehouse.Teacher;

public class SHA256PasswordManager implements PasswordManager {

    @Override
    public String hashPassword(String passwordForHash) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(passwordForHash.getBytes());
            byte[] data = md.digest();
            return DatatypeConverter.printHexBinary(data).toString();

        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        }
        return null;
    }

    private boolean isCorrectPassword(String passwordToCheck, String hashedPassword) {

        //return (hashPassword(passwordToCheck).equals(hashedPassword));
        return passwordToCheck.equals(hashedPassword);
    }

    @Override
    public boolean isCorrectPassword(String passwordToCheck, int userType, long id) {
        if (userType == 1) {
            Teacher teacher = DaoFactory.INSTANCE.getTeacherDao().findById(id);
            String hashedPassword = teacher.getPassword();
            return isCorrectPassword(passwordToCheck, hashedPassword);
        }

        if (userType == 2) {
            List<Admin> list = DaoFactory.INSTANCE.getAdminDao().getAll();
            for (Admin admin : list) {
                if (admin.getId().equals(id)) {
                    return isCorrectPassword(passwordToCheck, admin.getPassword());
                }
            }
        }
        if (userType == 3) {
            List<SuperAdmin> list = DaoFactory.INSTANCE.getSuperAdminDao().getAll();
            for (SuperAdmin sa : list) {
                if (sa.getId().equals(id)) {
                    return isCorrectPassword(passwordToCheck, sa.getPassword());
                }
            }
        }
        return false;
    }
}
