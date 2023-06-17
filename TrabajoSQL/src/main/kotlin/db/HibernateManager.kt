package db

import mu.KotlinLogging
import org.hibernate.Session
import java.io.Closeable
import java.sql.SQLException
import javax.persistence.EntityManager
import javax.persistence.EntityTransaction
import javax.persistence.Persistence


/**
 * Controlador de Entidades de Hibernate JPA
 */
val logger = KotlinLogging.logger {}

object HibernateManager : Closeable {
    private var entityManagerFactory = Persistence.createEntityManagerFactory("default")
    lateinit var manager: EntityManager
    private lateinit var transaction: EntityTransaction
    private var session: Session? = null


    init {
        manager = entityManagerFactory.createEntityManager()
        transaction = manager.transaction
    }

    fun open() {
        logger.debug { "Iniciando EntityManagerFactory" }
        manager = entityManagerFactory.createEntityManager()
        transaction = manager.transaction
        session = manager.unwrap(Session::class.java)
    }

    override fun close() {
        logger.debug { "Cerrando EntityManager" }
        session?.close()
        manager.close()
    }

    fun query(operations: () -> Unit) {
        open()
        try {
            operations()
        } catch (e: SQLException) {
            logger.error { "Error en la consulta: ${e.message}" }
        } finally {
            close()
        }
    }

    /**
     * Realiza unas operaciones en una transacción
     * @param operations operaciones a realizar
     * @throws SQLException si no se ha podido realizar la transacción
     */
    fun transaction(operations: () -> Unit) {
        open()
        try {
            logger.debug { "Transaction iniciada" }
            transaction.begin()
            operations()
            transaction.commit()
            logger.debug { "Transaction finalizada" }
        } catch (e: SQLException) {
            transaction.rollback()
            logger.error { "Error en la transacción: ${e.message}" }
            throw SQLException(e)
        } finally {
            close()
        }
    }
}