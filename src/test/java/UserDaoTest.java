import com.quizapp.dao.UserDao;
import com.quizapp.model.User;

public class UserDaoTest {
    static void main(String[] args) {
        User user=new User("abhishek","24110cn169@gmail.com","hqsgdyfwq","User");
        UserDao u=new UserDao();
       boolean res= u.registerUser(user);
        if(res) System.out.println("user is registered succesfull");
        else System.out.println("user didn't registered");
    }
}
