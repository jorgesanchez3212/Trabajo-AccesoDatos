package db
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine

object MongoDbManager {

    private lateinit var mongoClient : CoroutineClient
    internal lateinit var database : CoroutineDatabase

    private const val MONGO = "mongodb://localhost/itv?authSource=admin"


    init {
        mongoClient = KMongo.createClient("$MONGO").coroutine
        database = mongoClient.getDatabase("itv")

    }
}