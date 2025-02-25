package rule

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.Serializer
import kotlinx.coroutines.CoroutineScope
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import java.io.File

class ProtoDataStoreRule<T>(
    scope: CoroutineScope,
    serializer: Serializer<T>,
) : TestWatcher() {
    val dataStore get() = preferencesDataStore.dataStore
    private val preferencesDataStore =
        testProtoDataStore(scope, serializer)

    override fun finished(description: Description) {
        super.finished(description)
        preferencesDataStore.file.delete()
    }
}

/**
 * Creates a preferences data store suitable for use in JUnit unit tests.
 *
 * @param dir - The parent directory of the data store protobuf file.
 * @param scope - The scope in which IO operations and transform functions will execute.
 */
fun <T> testProtoDataStore(
    scope: CoroutineScope,
    serializer: Serializer<T>,
    dir: String = "build/intermediates/datastore",
): TestProtoDataStore<T> {
    val fileDir = File(dir).apply { mkdirs() }
    val file = File.createTempFile("dataStore", ".preferences_pb", fileDir)
    return TestProtoDataStore(
        DataStoreFactory.create(
            serializer = serializer,
            scope = scope,
        ) { file },
        file,
    )
}

class TestProtoDataStore<T>(
    val dataStore: DataStore<T>,
    val file: File,
)