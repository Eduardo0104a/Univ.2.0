package com.dwi.ruwana.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for managing JPA EntityManager instances
 */
public class JPAUtil {
    private static final Logger logger = LoggerFactory.getLogger(JPAUtil.class);
    private static final String PERSISTENCE_UNIT_NAME = "RuwanaPU";
    private static EntityManagerFactory entityManagerFactory;

    // Private constructor to prevent instantiation
    private JPAUtil() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Get the EntityManagerFactory instance
     */
    private static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
            synchronized (JPAUtil.class) {
                if (entityManagerFactory == null || !entityManagerFactory.isOpen()) {
                    try {
                        logger.info("Creating EntityManagerFactory for persistence unit: {}", PERSISTENCE_UNIT_NAME);
                        entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
                        logger.info("EntityManagerFactory created successfully");
                    } catch (Exception e) {
                        logger.error("Error creating EntityManagerFactory", e);
                        throw new RuntimeException("Failed to create EntityManagerFactory", e);
                    }
                }
            }
        }
        return entityManagerFactory;
    }

    /**
     * Get a new EntityManager instance
     */
    public static EntityManager getEntityManager() {
        try {
            EntityManager em = getEntityManagerFactory().createEntityManager();
            logger.debug("EntityManager created");
            return em;
        } catch (Exception e) {
            logger.error("Error creating EntityManager", e);
            throw new RuntimeException("Failed to create EntityManager", e);
        }
    }

    /**
     * Close the EntityManager if it's open
     */
    public static void close(EntityManager entityManager) {
        if (entityManager != null && entityManager.isOpen()) {
            try {
                entityManager.close();
                logger.debug("EntityManager closed");
            } catch (Exception e) {
                logger.warn("Error closing EntityManager", e);
            }
        }
    }

    /**
     * Close the EntityManagerFactory
     * Should be called when the application shuts down
     */
    public static void closeEntityManagerFactory() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            try {
                logger.info("Closing EntityManagerFactory");
                entityManagerFactory.close();
                logger.info("EntityManagerFactory closed successfully");
            } catch (Exception e) {
                logger.error("Error closing EntityManagerFactory", e);
            }
        }
    }

    /**
     * Execute a transaction with automatic commit/rollback
     */
    public static void executeInTransaction(TransactionCallback callback) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            callback.execute(em);
            
            em.getTransaction().commit();
            logger.debug("Transaction committed successfully");
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                logger.warn("Rolling back transaction due to error", e);
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Transaction failed", e);
        } finally {
            close(em);
        }
    }

    /**
     * Execute a transaction with automatic commit/rollback and return a result
     */
    public static <T> T executeInTransactionWithResult(TransactionCallbackWithResult<T> callback) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            
            T result = callback.execute(em);
            
            em.getTransaction().commit();
            logger.debug("Transaction committed successfully");
            return result;
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                logger.warn("Rolling back transaction due to error", e);
                em.getTransaction().rollback();
            }
            throw new RuntimeException("Transaction failed", e);
        } finally {
            close(em);
        }
    }

    /**
     * Callback interface for executing code within a transaction
     */
    @FunctionalInterface
    public interface TransactionCallback {
        void execute(EntityManager em);
    }

    /**
     * Callback interface for executing code within a transaction and returning a result
     */
    @FunctionalInterface
    public interface TransactionCallbackWithResult<T> {
        T execute(EntityManager em);
    }
}
