package com.writeit.write_it.dao.notebook_user;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.writeit.write_it.dao.crud.SoftCrudDAOImpl;
import com.writeit.write_it.dto.AllNotebooksDTO;
import com.writeit.write_it.dto.NotebookSummaryDTO;
import com.writeit.write_it.entity.NotebookUser;
import com.writeit.write_it.entity.enums.NotebookUserRole;

import jakarta.persistence.EntityManager;

@Repository
public class NotebookUserDAOImpl extends SoftCrudDAOImpl<Long, NotebookUser> implements NotebookUserDAO {
    public NotebookUserDAOImpl(EntityManager entityManager) {
        super(entityManager, NotebookUser.class);
    }

    @Override
    public AllNotebooksDTO getAllNotebooksByUserId(Long userId) {
        List<NotebookSummaryDTO> list = entityManager.createQuery("""
                select new com.writeit.write_it.dto.NotebookSummaryDTO(
                  nb.id, nb.name, nbu.role
                )
                from NotebookUser nbu
                join nbu.notebook nb
                where nbu.user.id = :userId
                order by
                  case nbu.role
                    when :roleOwner  then 1
                    when :roleEditor then 2
                    when :roleViewer then 3
                    else 9
                  end,
                  nb.name asc
                """, NotebookSummaryDTO.class)
                .setParameter("userId", userId)
                .setParameter("roleOwner", NotebookUserRole.OWNER)
                .setParameter("roleEditor", NotebookUserRole.EDITOR)
                .setParameter("roleViewer", NotebookUserRole.VIEWER)
                .getResultList();

        return new AllNotebooksDTO(list);
    }
}
