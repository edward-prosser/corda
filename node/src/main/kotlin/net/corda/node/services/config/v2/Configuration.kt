package net.corda.node.services.config.v2

import java.io.InputStream
import java.io.Reader
import java.nio.file.Path
import java.time.Duration
import java.util.*
import kotlin.reflect.KClass

interface Configuration {

    operator fun <TYPE> get(property: Configuration.Property<TYPE>): TYPE

    operator fun <TYPE> get(key: String): TYPE

    fun <TYPE> getOptional(property: Configuration.Property<TYPE>): TYPE?

    fun <TYPE> getOptional(key: String): TYPE?

    companion object {

        // TODO sollecitom perhaps try to use JvmStatic here
        // TODO sollecitom try to avoid knowing about `Konfiguration` here, perhaps by using a factory
        fun withSchema(schema: ConfigSchema): Configuration.Builder.Selector = Konfiguration.Builder.Selector(schema)
    }

    interface Builder {

        interface Selector {

            val from: Builder.SourceSelector

            val empty: Configuration.Builder

            val with: Builder.ValueSelector
        }

        val from: SourceSelector

        val with: ValueSelector

        operator fun <TYPE : Any> set(property: Property<TYPE>, value: TYPE)

        // TODO sollecitom maybe get rid of this
        fun build(): Configuration

        interface ValueSelector {

            fun <TYPE : Any> value(property: Configuration.Property<TYPE>, value: TYPE): Configuration.Builder
        }

        interface SourceSelector {

            fun systemProperties(prefixFilter: String = ""): Configuration.Builder

            fun environment(prefixFilter: String = ""): Configuration.Builder

            fun properties(properties: Properties): Configuration.Builder

            fun map(map: Map<String, Any>): Configuration.Builder

            fun hierarchicalMap(map: Map<String, Any>): Configuration.Builder

            val hocon: SourceSelector.FormatAware

            val yaml: SourceSelector.FormatAware

            val xml: SourceSelector.FormatAware

            val json: SourceSelector.FormatAware

            interface FormatAware {

                fun file(path: Path): Configuration.Builder

                fun resource(resourceName: String): Configuration.Builder

                fun reader(reader: Reader): Configuration.Builder

                fun inputStream(stream: InputStream): Configuration.Builder

                fun string(rawFormat: String): Configuration.Builder

                fun bytes(bytes: ByteArray): Configuration.Builder
            }
        }
    }

    interface Property<TYPE> : Validator<Configuration, ConfigValidationError> {

        val key: String
        val description: String
        val type: Class<TYPE>

        interface Optional<TYPE> : Configuration.Property<TYPE> {

            val defaultValue: TYPE
        }

        @Throws(Configuration.Exception.Missing::class, Configuration.Exception.WrongType::class, Configuration.Exception.BadValue::class)
        fun valueIn(configuration: Configuration): TYPE

        fun isSpecifiedBy(configuration: Configuration): Boolean

        fun optional(defaultValue: TYPE?): Configuration.Property<TYPE?>

        // TODO sollecitom see if you need this
        fun contextualize(currentContext: String?): String? = currentContext

        override fun validate(target: Configuration): Set<ConfigValidationError> {

            try {
                valueIn(target)
                return emptySet()
            } catch (exception: kotlin.Exception) {
                if (exception is Configuration.Exception) {
                    return setOf(exception.toValidationError(key, type))
                }
                throw exception
            }
        }

        // TODO sollecitom check
        private fun Configuration.Exception.toValidationError(keyName: String, type: Class<*>) = ConfigValidationError(keyName, type, message!!)

        companion object {

            // TODO sollecitom find a way of avoiding this double-linked dependency
            val ofType: Configuration.Property.Builder get() = Konfiguration.Property.Builder()
        }

        // TODO sollecitom maybe use a facade for type/typeList or to group all single vs list flavours
        interface Builder {

            fun int(key: String, description: String = ""): Configuration.Property<Int>
            fun intList(key: String, description: String = ""): Configuration.Property<List<Int>>

            fun boolean(key: String, description: String = ""): Configuration.Property<Boolean>
            fun booleanList(key: String, description: String = ""): Configuration.Property<List<Boolean>>

            fun double(key: String, description: String = ""): Configuration.Property<Double>
            fun doubleList(key: String, description: String = ""): Configuration.Property<List<Double>>

            fun string(key: String, description: String = ""): Configuration.Property<String>
            fun stringList(key: String, description: String = ""): Configuration.Property<List<String>>

            fun duration(key: String, description: String = ""): Configuration.Property<Duration>
            fun durationList(key: String, description: String = ""): Configuration.Property<List<Duration>>

            fun <ENUM : Enum<ENUM>> enum(key: String, enumClass: KClass<ENUM>, description: String = ""): Configuration.Property<ENUM>
            fun <ENUM : Enum<ENUM>> enumList(key: String, enumClass: KClass<ENUM>, description: String = ""): Configuration.Property<List<ENUM>>

            fun nested(key: String, schema: ConfigSchema, description: String = ""): Configuration.Property<Configuration>
            fun nestedList(key: String, schema: ConfigSchema, description: String = ""): Configuration.Property<List<Configuration>>
        }
    }

    // TODO sollecitom add message and cause
    sealed class Exception : kotlin.Exception() {

        // TODO sollecitom add fields
        class Missing : Configuration.Exception()

        // TODO sollecitom add fields
        class WrongType : Configuration.Exception()

        // TODO sollecitom add fields
        class BadValue : Configuration.Exception()
    }
}