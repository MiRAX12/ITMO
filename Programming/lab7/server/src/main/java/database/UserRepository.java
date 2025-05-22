//package database;
//
//public class UserRepository {
//
//    boolean existsByUsername(String username);
//
//    @Query("SELECT u FROM User u WHERE u.username = :username")
//    User findByUsername(@Param("username") String username);
//
//    @Query("SELECT u.id FROM User u WHERE u.username = :usernameString")
//    long findUserIdByUsername(@Param("usernameString") String usernameString);
//}
