package repository;


import domain.Employee;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDao {

    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Employee e){
        String query = "insert into employee values(" + e.getId() + ",\""
                + e.getName() + "\"," + e.getSalary() + ")";
        return jdbcTemplate.update(query);
    }

    //example for prepared statement
    public Boolean updateEmployeeByPreparedStatement(Employee e){
        String query="update employee set name= ?,salary= ? where id= ?";
        return jdbcTemplate.execute(query, new PreparedStatementCallback<Boolean>() {
            @Override
            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                ps.setString(1, e.getName());
                ps.setFloat(2, e.getSalary());
                ps.setInt(3, e.getId());
                return ps.execute();
            }
        });
    }
    public int deleteEmployee(Employee e){
        String query = "delete from employee where id='"+e.getId()+"' ";
        return jdbcTemplate.update(query);
    }

    ////example for prepared statement resultSet extractor
        public List<Employee> findAll(){
        String query = "select * from employee";
        return jdbcTemplate.query(query, new ResultSetExtractor<List<Employee>>() {
            @Override
            public List<Employee> extractData(ResultSet rs) throws SQLException, DataAccessException {
                List<Employee> employees = new ArrayList<>();
                Employee e;
                while (rs.next()){
                    e = new Employee(rs.getInt(1), rs.getString(2), rs.getFloat(3));
                    employees.add(e);
                }
                return employees;
            }
        });
    }

}
