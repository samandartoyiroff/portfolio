package uz.tuit.portfolio.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.tuit.portfolio.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);

    Optional<User> findByEmail(String email);

    @Query(value = """
    SELECT * 
    FROM users u
    WHERE u.id <> :id
      AND u.id NOT IN (SELECT a.user_id FROM admin a)
      AND (
          :query IS NULL 
          OR :query = '' 
          OR (
              u.username ILIKE CONCAT('%', :query, '%')
              OR u.full_name ILIKE CONCAT('%', :query, '%')
              OR u.email ILIKE CONCAT('%', :query, '%')
              OR u.phone_number ILIKE CONCAT('%', :query, '%')
              OR similarity(u.username, :query) > 0.3
              OR similarity(u.full_name, :query) > 0.3
              OR similarity(u.email, :query) > 0.3
              OR similarity(u.phone_number, :query) > 0.3
          )
      )
    ORDER BY 
        GREATEST(
            similarity(u.username, COALESCE(:query, '')),
            similarity(u.full_name, COALESCE(:query, '')),
            similarity(u.email, COALESCE(:query, '')),
            similarity(u.phone_number, COALESCE(:query, ''))
        ) DESC
    """, nativeQuery = true)
    List<User> searchUsersExceptMeAndAdmins(
            @Param("id") Long id,
            @Param("query") String query
    );



    @Query(value = """
    SELECT * 
    FROM users u
    WHERE 
        -- Har doim o'zini chiqarib tashlash
        u.id <> :id

        -- Agar foydalanuvchi superadmin bo'lsa, hech kimni tashlab ketmaydi (faqat o'zini)
        AND (
            NOT EXISTS (
                SELECT 1 FROM users_roles ur
                JOIN roles r ON ur.roles_id = r.id
                WHERE ur.user_id = :id
                AND r.name = 'ROLE_SUPERADMIN'
            )
            -- Agar admin bo'lsa → boshqa adminlarni chiqarib tashlaymiz
            AND u.id NOT IN (
                SELECT a.user_id FROM admin a
            )
            OR EXISTS (
                SELECT 1 FROM users_roles ur
                JOIN roles r ON ur.roles_id = r.id
                WHERE ur.user_id = :id
                AND r.name = 'ROLE_SUPERADMIN'
            )
        )

        -- Qidiruv (agar query null yoki bo‘sh bo‘lsa → hammasi chiqadi)
        AND (
            :query IS NULL 
            OR :query = '' 
            OR (
                u.username ILIKE CONCAT('%', :query, '%')
                OR u.full_name ILIKE CONCAT('%', :query, '%')
                OR u.email ILIKE CONCAT('%', :query, '%')
                OR u.phone_number ILIKE CONCAT('%', :query, '%')
                OR similarity(u.username, :query) > 0.3
                OR similarity(u.full_name, :query) > 0.3
                OR similarity(u.email, :query) > 0.3
                OR similarity(u.phone_number, :query) > 0.3
            )
        )

    ORDER BY 
        GREATEST(
            similarity(u.username, COALESCE(:query, '')),
            similarity(u.full_name, COALESCE(:query, '')),
            similarity(u.email, COALESCE(:query, '')),
            similarity(u.phone_number, COALESCE(:query, ''))
        ) DESC
    """, nativeQuery = true)
    List<User> findAllUsers(@Param("id") Long id, @Param("query") String query);



}
