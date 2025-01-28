package com.group44.assignment2.storage;

import com.group44.assignment2.models.*;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.query.In;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;

public class Storage {

    private final String dbUrl;
    private final ConnectionSource connectionSource;

    public Storage(String dbUrl) throws SQLException {
        this.dbUrl = dbUrl;

        this.connectionSource = new JdbcConnectionSource(this.dbUrl);
        this.initializeTables();
    }


    public ArrayList<Employee> getEmployees() throws SQLException {
        ArrayList<Employee> ret = new ArrayList<>();
        // Read data
        ret.addAll(this.getDao(Permanent.class).queryForAll());
        ret.addAll(this.getDao(Manager.class).queryForAll());
        ret.addAll(this.getDao(Contractor.class).queryForAll());

        ret.forEach(employee -> employee.postLoad());

        return ret;
    }

    public void upsertEmployees(ArrayList<Employee> employee) throws SQLException {
        // Iterate over array and add or update record in corresponding table
        for (Employee emp : employee) {
            if (emp instanceof Manager) {
                this.getDao(Manager.class).createOrUpdate((Manager) emp);
            } else if (emp instanceof Permanent) {
                this.getDao(Permanent.class).createOrUpdate((Permanent) emp);
            } else if (emp instanceof Contractor) {
                this.getDao(Contractor.class).createOrUpdate((Contractor) emp);
            } else {
                throw new IllegalArgumentException("Unsupported employee type: " + emp.getClass().getName());
            }
        }
    }

    public void upsertEmployee(Employee employee) throws SQLException {
        if (employee instanceof Manager) {
            this.getDao(Manager.class).createOrUpdate((Manager) employee);
        } else if (employee instanceof Permanent) {
            this.getDao(Permanent.class).createOrUpdate((Permanent) employee);
        } else if (employee instanceof Contractor) {
            this.getDao(Contractor.class).createOrUpdate((Contractor) employee);
        } else {
            throw new IllegalArgumentException("Unsupported employee type: " + employee.getClass().getName());
        }
    }

    public void deleteEmployee(Employee employee) throws SQLException {
        if (employee instanceof Manager) {
            this.getDao(Manager.class).delete((Manager) employee);
        } else if (employee instanceof Permanent) {
            this.getDao(Permanent.class).delete((Permanent) employee);
        } else if (employee instanceof Contractor) {
            this.getDao(Contractor.class).delete((Contractor) employee);
        } else {
            throw new IllegalArgumentException("Unsupported employee type: " + employee.getClass().getName());
        }
    }


    public ArrayList<Payroll> getPayrolls() throws SQLException {
        return new ArrayList<>(this.getDao(Payroll.class).queryForAll());
    }

    public void upsertPayrolls(ArrayList<Payroll> payrolls) throws SQLException {
        Dao<Payroll, Integer> dao = this.getDao(Payroll.class);

        for (Payroll p : payrolls) {
            dao.createOrUpdate(p);
        }
    }

    public void upsertPayroll(Payroll p) throws SQLException {
        Dao<Payroll, Integer> dao = this.getDao(Payroll.class);
        dao.createOrUpdate(p);
    }

    public void deletePayroll(Payroll p) throws SQLException {
        Dao<Payroll, Integer> dao = this.getDao(Payroll.class);
        dao.delete(p);
    }

    public ArrayList<Shift> getShifts() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, Shift.class);

        Dao<Shift, Integer> dao = DaoManager.createDao(this.connectionSource, Shift.class);

        return new ArrayList<>(dao.queryForAll());
    }

    public void upsertShifts(ArrayList<Shift> shifts) throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, Shift.class);

        Dao<Shift, Integer> dao = DaoManager.createDao(this.connectionSource, Shift.class);

        for (Shift s : shifts) {
            dao.createOrUpdate(s);
        }
    }

    public void upsertShift(Shift s) throws SQLException {
        Dao<Shift, Integer> dao = this.getDao(Shift.class);
        dao.createOrUpdate(s);
    }

    public void removeShift(Shift shift) throws SQLException {
        Dao<Shift, Integer> dao = this.getDao(Shift.class);
        dao.delete(shift);
    }

    public ArrayList<Department> getDepartments() throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, Department.class);

        Dao<Department, Integer> dao = DaoManager.createDao(this.connectionSource, Department.class);

        return new ArrayList<>(dao.queryForAll());
    }

    public void upsertDepartments(ArrayList<Department> departments) throws SQLException {
        TableUtils.createTableIfNotExists(connectionSource, Department.class);

        Dao<Department, Integer> dao = DaoManager.createDao(this.connectionSource, Department.class);

        for (Department d : departments) {
            dao.createOrUpdate(d);
        }
    }

    public void upsertDepartment(Department department) throws SQLException {
        Dao<Department, Integer> dao = this.getDao(Department.class);
        dao.createOrUpdate(department);
    }

    public void removeDepartment(Department department) throws SQLException {
        Dao<Department, Integer> dao = this.getDao(Department.class);
        dao.delete(department);
    }

    public int getEmployeeMaxId() throws SQLException {
        int maxManagerId = getDao(Manager.class).executeRaw("SELECT MAX(id) FROM managers");
        int maxContractorId = getDao(Contractor.class).executeRaw("SELECT MAX(id) FROM contractors");
        int maxPermanentId = getDao(Permanent.class).executeRaw("SELECT MAX(id) FROM permanents");
        return Math.max(maxManagerId, Math.max(maxContractorId, maxPermanentId));
    }

    private void initializeTables() throws SQLException {
        Class<?>[] entityClasses = {
                Permanent.class, Manager.class, Contractor.class,
                Payroll.class, Shift.class, Department.class
        };
        for (Class<?> entityClass : entityClasses) {
            TableUtils.createTableIfNotExists(this.connectionSource, entityClass);
        }
    }


    private <T, ID> Dao<T, ID> getDao(Class<T> entityClass) throws SQLException {
        return DaoManager.createDao(connectionSource, entityClass);
    }


    // TODO: complete this method
    // public <T> void upsert(T[] entities){
    //     for (T entity : entities) {
    //         this.getDao(T.class);
    //     }
    // }
}
