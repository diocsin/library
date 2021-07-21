package com.example.library.library.repository;


import com.example.library.library.model.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;


@Repository
public class UserRepositoryCastomImpl implements UserRepositoryCastom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<User> filterUser(String filterStr) {
        Session session = em.unwrap(Session.class);
        Criteria criteria = session.createCriteria(User.class);
        criteria.createAlias("roles", "roles");
        Disjunction disjunction = Restrictions.or(
                Restrictions.ilike("firstName", filterStr, MatchMode.ANYWHERE),
                Restrictions.ilike("lastName", filterStr, MatchMode.ANYWHERE),
                Restrictions.ilike("middleName", filterStr, MatchMode.ANYWHERE),
                Restrictions.ilike("login", filterStr, MatchMode.ANYWHERE),
                Restrictions.ilike("roles.pid", filterStr, MatchMode.ANYWHERE)
        );
        criteria.add(disjunction);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }
}
