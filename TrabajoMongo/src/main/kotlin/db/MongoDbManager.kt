package db
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import java.util.*

object MongoDbManager {

    private lateinit var mongoClient : CoroutineClient
    internal lateinit var database : CoroutineDatabase

    private val properties = Properties()

    //private const val MONGO = "mongodb://localhost/itv?authSource=admin"


    init {
        mongoClient = KMongo.createClient(properties.getProperty("mongo.connection").toString()).coroutine
        database = mongoClient.getDatabase("itv")

    }
}