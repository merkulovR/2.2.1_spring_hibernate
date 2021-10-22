package hiber.dao;

import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      return sessionFactory.getCurrentSession().createQuery("from User").getResultList();
   }

   @Override
   @SuppressWarnings("unchecked")
   public User getUserByCarModelAndSeries(String carModel, int carSeries) {
      return sessionFactory.getCurrentSession()
              .createQuery("select u from User u " +
                      "inner join u.car as car " +
                      "where car.model = :paramModel " +
                      "and car.series = :paramSeries", User.class)
              .setParameter("paramModel", carModel)
              .setParameter("paramSeries", carSeries)
              .getSingleResult();
   }

}
