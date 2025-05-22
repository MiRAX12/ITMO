//package database;
//
//public class UserService {
//    private final UserRepository userRepository;
//
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public boolean getUserExists(String username) {
//        return userRepository.existsByUsername(username);
//    }
//
//    public User getUserByUsername(String username) {
//        return userRepository.findByUsername(username);
//    }
//
//    public User saveUser(User user) {
//        return userRepository.save(user);
//    }
//
//    public long getId(String username) {
//        return userRepository.findUserIdByUsername(username);
//    }
//
//}
