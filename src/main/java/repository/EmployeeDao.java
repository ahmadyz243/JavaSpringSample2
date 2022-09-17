package repository;


import domain.Employee;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeDao {

    private JdbcTemplate jdbcTemplate;
    private NamedParameterJdbcTemplate jTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(Employee e){
        String query = "insert into employee values(" + e.getId() + ",\""
                + e.getName() + "\"," + e.getSalary() + ")";
        return jdbcTemplate.update(query);
    }

    //example for prepared statement & SimpleJdbcTemplate
    public int updateEmployee(Employee e){
        String query="update employee set name= ?,salary= ? where id= ?";

        return jdbcTemplate.update(query, e.getName(), e.getSalary(), e.getId());

        // another way for update
//        return jdbcTemplate.execute(query, new PreparedStatementCallback<Boolean>() {
//            @Override
//            public Boolean doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
//                ps.setString(1, e.getName());
//                ps.setFloat(2, e.getSalary());
//                ps.setInt(3, e.getId());
//                return ps.execute();
//            }
//        });
    }
    public int deleteEmployee(Employee e){
        String query = "delete from employee where id='"+e.getId()+"' ";
        return jdbcTemplate.update(query);
    }

    //example for prepared statement resultSet extractor
//        public List<Employee> findAll(){
//        String query = "select * from employee";
//        return jdbcTemplate.query(query, new ResultSetExtractor<List<Employee>>() {
//            @Override
//            public List<Employee> extractData(ResultSet rs) throws SQLException, DataAccessException {
//                List<Employee> employees = new ArrayList<>();
//                Employee e;
//                while (rs.next()){
//                    e = new Employee(rs.getInt(1), rs.getString(2), rs.getFloat(3));
//                    employees.add(e);
//                }
//                return employees;
//            }
//        });
//    }

    //example for prepared statement RowMapper
    public List<Employee> findAll(){
        String query = "select * from employee";
        return jdbcTemplate.query(query, new RowMapper<Employee>() {
            @Override
            public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Employee(rs.getInt(1),
                        rs.getString(2), rs.getFloat(3));
            }
        });
    }

    // example for named parameter
    public  void saveWithNamedParameter (Employee e){
        String query="insert into employee values(:id,:name,:salary)";

        Map<String,Object> map=new HashMap<String,Object>();
        map.put("id",e.getId());
        map.put("name",e.getName());
        map.put("salary",e.getSalary());

        jTemplate.execute(query, map, new PreparedStatementCallback<Object>() {
            @Override
            public Object doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
                return ps.executeUpdate();
            }
        });
    }

    public NamedParameterJdbcTemplate getjTemplate() {
        return jTemplate;
    }

    public void setjTemplate(NamedParameterJdbcTemplate jTemplate) {
        this.jTemplate = jTemplate;
    }
}
