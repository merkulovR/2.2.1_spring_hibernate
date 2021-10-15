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
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   @SuppressWarnings("unchecked")
   public User getUserByCar(String carModel, int carSeries) {
      TypedQuery<Long> carQuery = sessionFactory.getCurrentSession().createQuery("select id from Car car where car.model = :paramModel and car.series = :paramSeries");
      carQuery.setParameter("paramModel", carModel);
      carQuery.setParameter("paramSeries", carSeries);
      Long id = carQuery.getSingleResult();

      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User user where user.car.id = :paramCarId");
      query.setParameter("paramCarId", id);
      return query.getSingleResult();
   }

}
