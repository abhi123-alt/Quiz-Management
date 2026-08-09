import com.quizapp.dao.UserDAO;
import com.quizapp.model.User;

public class UserDaoTest {
    static void main(String[] args) {
        User user=new User("abhishek","abhishekgoswami965@gmail.com","Abhishek@123");
        UserDAO u=new UserDAO();
       boolean res= u.registerUser(user);
        if(res) System.out.println("user is registered succesfull");
        else System.out.println("user didn't registered");
    }
}
