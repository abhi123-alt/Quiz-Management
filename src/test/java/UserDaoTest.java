import com.quizapp.dao.UserDAO;
import com.quizapp.model.User;

public class UserDaoTest {
    static void main(String[] args) {
        //User user=new User("abhishek","abhishekgoswami@gmail.com","Abhishek@123");
        UserDAO u=new UserDAO();
        User user=u.getUserByEmail("abhishekgoswami@gmail.com","user");
        System.out.println(user.getName());
    }
}
