package com.inmu.nanoforum.dao;

import com.inmu.nanoforum.model.AppUser;
import com.inmu.nanoforum.model.Message;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("messageDao")
public class MessageDaoImpl implements MessageDao {

    private SessionFactory sessionFactory;
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Message> getInbox(AppUser appUser) {
        Session session = sessionFactory.getCurrentSession();

        Query<Message> query = session.createQuery("from Message where receiverId=:userId order by sendTime", Message.class);

        query.setParameter("userId", appUser.getId());

        return query.getResultList();
    }

    @Override
    public List<Message> getOutbox(AppUser appUser) {
        Session session = sessionFactory.getCurrentSession();

        Query<Message> query = session.createQuery("from Message where senderId=:userId order by sendTime", Message.class);

        query.setParameter("userId", appUser.getId());

        return query.getResultList();
    }

    @Override
    public void save(Message message) {
        Session session = sessionFactory.getCurrentSession();

        session.saveOrUpdate(message);
    }

    @Override
    public Message getById(int msgId) {
        Session session = sessionFactory.getCurrentSession();

        return session.get(Message.class, msgId);
    }

}
