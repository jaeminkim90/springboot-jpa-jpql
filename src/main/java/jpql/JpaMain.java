package jpql;

import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // transaction 시작

        try {


            em.flush();
            em.clear();

            String query =
                    "select " +
                            "case when m.age <= 10 then '학생요금'" +
                            "   when m.age <= 60 then '경로요금'" +
                            " else '일반요금' " +
                            "from Member m";
            List<String> result = em.createQuery(query, String.class).getResultList();
            for (String s : result) {
                System.out.println("s = " + s);
            }


            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close(); // 사용이 끝나면 닫아준다
        }
        emf.close(); // 완전히 끝나면 Factory도 닫아준다
    }
}
