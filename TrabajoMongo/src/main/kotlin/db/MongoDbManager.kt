package db
import org.litote.kmongo.reactivestreams.KMongo
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import java.util.*

object MongoDbManager {

    private lateinit var mongoClient: CoroutineClient
    internal lateinit var database: CoroutineDatabase

    private const val MONGO_TYPE = "mongodb://"
    private const val HOST = "localhost"
    private const val DATABASE = "itv"
    private const val OPTIONS = "?authSource=admin"


    private const val MONGO_URI =
        "$MONGO_TYPE$HOST/$DATABASE"



    init {
        println("MongoDB -> $MONGO_URI$OPTIONS")
        mongoClient =
            KMongo.createClient("$MONGO_URI$OPTIONS").coroutine
        database = mongoClient.getDatabase("itv")
    }
}