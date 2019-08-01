package com.transportation.bookcar.core

import android.util.Log
import android.util.Log.getStackTraceString
import org.jetbrains.annotations.NonNls
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*
import java.util.Collections.*
import java.util.regex.*

/**
 * Class for logging function
 */
@SuppressWarnings("unused")
sealed class AppLogger {
    
    /** A facade for handling logging calls. Install instances via [AppLogger.plant()][.plant].  */
    abstract class Tree {
        internal val explicitTag = ThreadLocal<String>()
        
        internal open val tag: String?
            get() {
                val tag = explicitTag.get()
                if (tag != null) {
                    explicitTag.remove()
                }
                return tag
            }
        
        /** Log a verbose message with optional format args.  */
        open fun v(message: String, vararg args: Any) {
            prepareLog(Log.VERBOSE, null, message, *args)
        }
        
        /** Log a verbose exception and a message with optional format args.  */
        open fun v(t: Throwable, message: String, vararg args: Any) {
            prepareLog(Log.VERBOSE, t, message, *args)
        }
        
        /** Log a verbose exception.  */
        open fun v(t: Throwable) {
            prepareLog(Log.VERBOSE, t, null)
        }
        
        /** Log a debug message with optional format args.  */
        open fun d(message: String, vararg args: Any) {
            prepareLog(Log.DEBUG, null, message, *args)
        }
        
        /** Log a debug exception and a message with optional format args.  */
        open fun d(t: Throwable, message: String, vararg args: Any) {
            prepareLog(Log.DEBUG, t, message, *args)
        }
        
        /** Log a debug exception.  */
        open fun d(t: Throwable) {
            prepareLog(Log.DEBUG, t, null)
        }
        
        /** Log an info message with optional format args.  */
        open fun i(message: String, vararg args: Any) {
            prepareLog(Log.INFO, null, message, *args)
        }
        
        /** Log an info exception and a message with optional format args.  */
        open fun i(t: Throwable, message: String, vararg args: Any) {
            prepareLog(Log.INFO, t, message, *args)
        }
        
        /** Log an info exception.  */
        open fun i(t: Throwable) {
            prepareLog(Log.INFO, t, null)
        }
        
        /** Log a warning message with optional format args.  */
        open fun w(message: String, vararg args: Any) {
            prepareLog(Log.WARN, null, message, *args)
        }
        
        /** Log a warning exception and a message with optional format args.  */
        open fun w(t: Throwable, message: String, vararg args: Any) {
            prepareLog(Log.WARN, t, message, *args)
        }
        
        /** Log a warning exception.  */
        open fun w(t: Throwable) {
            prepareLog(Log.WARN, t, null)
        }
        
        /** Log an error message with optional format args.  */
        open fun e(message: String, vararg args: Any) {
            prepareLog(Log.ERROR, null, message, *args)
        }
        
        /** Log an error exception and a message with optional format args.  */
        open fun e(t: Throwable, message: String, vararg args: Any) {
            prepareLog(Log.ERROR, t, message, *args)
        }
        
        /** Log an error exception.  */
        open fun e(t: Throwable) {
            prepareLog(Log.ERROR, t, null)
        }
        
        /** Log an assert message with optional format args.  */
        open fun wtf(message: String, vararg args: Any) {
            prepareLog(Log.ASSERT, null, message, *args)
        }
        
        /** Log an assert exception and a message with optional format args.  */
        open fun wtf(t: Throwable, message: String, vararg args: Any) {
            prepareLog(Log.ASSERT, t, message, *args)
        }
        
        /** Log an assert exception.  */
        open fun wtf(t: Throwable) {
            prepareLog(Log.ASSERT, t, null)
        }
        
        /** Log at `priority` a message with optional format args.  */
        open fun log(priority: Int, message: String, vararg args: Any) {
            prepareLog(priority, null, message, *args)
        }
        
        /** Log at `priority` an exception and a message with optional format args.  */
        open fun log(priority: Int, t: Throwable, message: String, vararg args: Any) {
            prepareLog(priority, t, message, *args)
        }
        
        /** Log at `priority` an exception.  */
        open fun log(priority: Int, t: Throwable) {
            prepareLog(priority, t, null)
        }
        
        /**
         * Return whether a message at `priority` should be logged.
         */
        @SuppressWarnings("unused")
        protected fun isLoggable(priority: Int): Boolean {
            return true
        }
        
        /** Return whether a message at `priority` or `tag` should be logged.  */
        @SuppressWarnings("unused")
        protected fun isLoggable(tag: String?, priority: Int): Boolean {
            return isLoggable(priority)
        }
        
        private fun prepareLog(priority: Int, t: Throwable?, message: String?, vararg args: Any) {
            // Consume tag even when message is not loggable so that next message is correctly tagged.
            val tag = tag
            
            if (!isLoggable(tag, priority)) {
                return
            }
            var logMessage: String = ""
            if (message.isNullOrBlank()) {
                if (t == null) {
                    return  // Swallow message if it's null and there's no throwable.
                }
                logMessage = getStackTraceString(t)
            }
            else {
                if (args.size > 0) {
                    logMessage = formatMessage(message!!, args)
                }
                t.let {
                    logMessage = """${logMessage}
                    |${getStackTraceString(it)}"""
                }
            }
            
            log(priority, tag, logMessage, t)
        }
        
        /**
         * Formats a log message with optional arguments.
         */
        protected fun formatMessage(message: String, args: Array<out Any>): String {
            return String.format(message, *args)
        }
        
        private fun getStackTraceString(t: Throwable): String {
            // Don't replace this with Log.getStackTraceString() - it hides
            // UnknownHostException, which is not what we want.
            val sw = StringWriter(256)
            val pw = PrintWriter(sw, false)
            t.printStackTrace(pw)
            pw.flush()
            return sw.toString()
        }
        
        /**
         * Write a log message to its destination. Called for all level-specific methods by default.
         *
         * @param priority Log level. See [Log] for constants.
         * @param tag Explicit or inferred tag. May be `null`.
         * @param message Formatted log message. May be `null`, but then `t` will not be.
         * @param t Accompanying exceptions. May be `null`, but then `message` will not be.
         */
        protected abstract fun log(priority: Int, tag: String?, message: String, t: Throwable?)
    }
    
    /** A [Tree] for debug builds. Automatically infers the tag from the calling class.  */
    class DebugTree : Tree() {
        
        override // DO NOT switch this to Thread.getCurrentThread().getStackTrace(). The test will pass
        // because Robolectric runs them on the JVM but on Android the elements are different.
        val tag: String?
            get() {
                val tag = super.tag
                if (tag != null) {
                    return tag
                }
                val stackTrace = Throwable().stackTrace
                if (stackTrace.size <= CALL_STACK_INDEX) {
                    throw IllegalStateException(
                            "Synthetic stacktrace didn't have enough elements: are you using proguard?"
                    )
                }
                return createStackElementTag(stackTrace[CALL_STACK_INDEX])
            }
        
        /**
         * Extract the tag which should be used for the message from the `element`. By default
         * this will use the class name without any anonymous class suffixes (e.g., `Foo$1`
         * becomes `Foo`).
         *
         *
         * Note: This will not be called if a [manual tag][.tag] was specified.
         */
        protected fun createStackElementTag(element: StackTraceElement): String {
            var tag = element.className
            val m = ANONYMOUS_CLASS.matcher(tag)
            if (m.find()) {
                tag = m.replaceAll("")
            }
            tag = tag.substring(tag.lastIndexOf('.') + 1)
            return if (tag.length > MAX_TAG_LENGTH) tag.substring(0, MAX_TAG_LENGTH) else tag
        }
        
        /**
         * Break up `message` into maximum-length chunks (if needed) and send to either
         * [Log.println()][Log.println] or
         * [Log.wtf()][Log.wtf] for logging.
         *
         * {@inheritDoc}
         */
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            if (message.length < MAX_LOG_LENGTH) {
                if (priority == Log.ASSERT) {
                    Log.wtf(tag, message)
                }
                else {
                    Log.println(priority, tag, message)
                }
                return
            }
            
            // Split by line, then ensure each line can fit into Log's maximum length.
            var i = 0
            val length = message.length
            while (i < length) {
                var newline = message.indexOf('\n', i)
                newline = if (newline != -1) newline else length
                do {
                    val end = Math.min(newline, i + MAX_LOG_LENGTH)
                    val part = message.substring(i, end)
                    if (priority == Log.ASSERT) {
                        Log.wtf(tag, part)
                    }
                    else {
                        Log.println(priority, tag, part)
                    }
                    i = end
                }
                while (i < newline)
                i++
            }
        }
        
        companion object {
            private const val MAX_LOG_LENGTH = 4000
            private const val MAX_TAG_LENGTH = 23
            private const val CALL_STACK_INDEX = 5
            private val ANONYMOUS_CLASS = Pattern.compile("(\\$\\d+)+$")
        }
    }
    
    companion object {
        /** Log a verbose message with optional format args.  */
        @SuppressWarnings("unused")
        fun v(@NonNls message: String, vararg args: Any) {
            TREE_OF_SOULS.v(message, *args)
        }
        
        /** Log a verbose exception and a message with optional format args.  */
        @SuppressWarnings("unused")
        fun v(t: Throwable, @NonNls message: String, vararg args: Any) {
            TREE_OF_SOULS.v(t, message, *args)
        }
        
        /** Log a verbose exception.  */
        @SuppressWarnings("unused")
        fun v(t: Throwable) {
            TREE_OF_SOULS.v(t)
        }
        
        /** Log a debug message with optional format args.  */
        @SuppressWarnings("unused")
        fun d(@NonNls message: String, vararg args: Any) {
            TREE_OF_SOULS.d(message, *args)
        }
        
        /** Log a debug exception and a message with optional format args.  */
        @SuppressWarnings("unused")
        fun d(t: Throwable, @NonNls message: String, vararg args: Any) {
            TREE_OF_SOULS.d(t, message, *args)
        }
        
        /** Log a debug exception.  */
        @SuppressWarnings("unused")
        fun d(t: Throwable) {
            TREE_OF_SOULS.d(t)
        }
        
        /** Log an info message with optional format args.  */
        @SuppressWarnings("unused")
        fun i(@NonNls message: String, vararg args: Any) {
            TREE_OF_SOULS.i(message, *args)
        }
        
        /** Log an info exception and a message with optional format args.  */
        @SuppressWarnings("unused")
        fun i(t: Throwable, @NonNls message: String, vararg args: Any) {
            TREE_OF_SOULS.i(t, message, *args)
        }
        
        /** Log an info exception.  */
        @SuppressWarnings("unused")
        fun i(t: Throwable) {
            TREE_OF_SOULS.i(t)
        }
        
        /** Log a warning message with optional format args.  */
        @SuppressWarnings("unused")
        fun w(@NonNls message: String, vararg args: Any) {
            TREE_OF_SOULS.w(message, *args)
        }
        
        /** Log a warning exception and a message with optional format args.  */
        @SuppressWarnings("unused")
        fun w(t: Throwable, @NonNls message: String, vararg args: Any) {
            TREE_OF_SOULS.w(t, message, *args)
        }
        
        /** Log a warning exception.  */
        @SuppressWarnings("unused")
        fun w(t: Throwable) {
            TREE_OF_SOULS.w(t)
        }
        
        /** Log an error message with optional format args.  */
        @SuppressWarnings("unused")
        fun e(@NonNls message: String, vararg args: Any) {
            TREE_OF_SOULS.e(message, *args)
        }
        
        /** Log an error exception and a message with optional format args.  */
        @SuppressWarnings("unused")
        fun e(t: Throwable, @NonNls message: String, vararg args: Any) {
            TREE_OF_SOULS.e(t, message, *args)
        }
        
        /** Log an error exception.  */
        @SuppressWarnings("unused")
        fun e(t: Throwable) {
            TREE_OF_SOULS.e(t)
        }
        
        /** Log an assert message with optional format args.  */
        @SuppressWarnings("unused")
        fun wtf(@NonNls message: String, vararg args: Any) {
            TREE_OF_SOULS.wtf(message, *args)
        }
        
        /** Log an assert exception and a message with optional format args.  */
        @SuppressWarnings("unused")
        fun wtf(t: Throwable, @NonNls message: String, vararg args: Any) {
            TREE_OF_SOULS.wtf(t, message, *args)
        }
        
        /** Log an assert exception.  */
        @SuppressWarnings("unused")
        fun wtf(t: Throwable) {
            TREE_OF_SOULS.wtf(t)
        }
        
        /** Log at `priority` a message with optional format args.  */
        @SuppressWarnings("unused")
        fun log(priority: Int, @NonNls message: String, vararg args: Any) {
            TREE_OF_SOULS.log(priority, message, *args)
        }
        
        /** Log at `priority` an exception and a message with optional format args.  */
        @SuppressWarnings("unused")
        fun log(priority: Int, t: Throwable, @NonNls message: String, vararg args: Any) {
            TREE_OF_SOULS.log(priority, t, message, *args)
        }
        
        /** Log at `priority` an exception.  */
        @SuppressWarnings("unused")
        fun log(priority: Int, t: Throwable) {
            TREE_OF_SOULS.log(priority, t)
        }
        
        /**
         * A view into AppLogger's planted trees as a tree itself. This can be used for injecting a logger
         * instance rather than using static methods or to facilitate testing.
         */
        @SuppressWarnings("unused")
        fun asTree(): Tree {
            return TREE_OF_SOULS
        }
        
        /** Set a one-time tag for use on the next logging call.  */
        @SuppressWarnings("unused")
        fun tag(tag: String): Tree {
            val forest = forestAsArray
            
            var i = 0
            val count = forest.size
            while (i < count) {
                forest[i].explicitTag.set(tag)
                i++
            }
            return TREE_OF_SOULS
        }
        
        /** Add a new logging tree.  */
        @SuppressWarnings("unused")
        fun plant(tree: Tree) {
            if (tree === TREE_OF_SOULS) {
                throw IllegalArgumentException("Cannot plant AppLogger into itself.")
            }
            synchronized(FOREST) {
                FOREST.add(tree)
                forestAsArray = FOREST.toTypedArray<Tree>()
            }
        }
        
        /** Adds new logging trees.  */
        @SuppressWarnings("unused")
        fun plant(vararg trees: Tree) {
            for (tree in trees) {
                if (tree === TREE_OF_SOULS) {
                    throw IllegalArgumentException("Cannot plant AppLogger into itself.")
                }
            }
            synchronized(FOREST) {
                Collections.addAll(FOREST, *trees)
                forestAsArray = FOREST.toTypedArray<Tree>()
            }
        }
        
        /** Remove a planted tree.  */
        @SuppressWarnings("unused")
        fun uproot(tree: Tree) {
            synchronized(FOREST) {
                if (!FOREST.remove(tree)) {
                    throw IllegalArgumentException("Cannot uproot tree which is not planted: " + tree)
                }
                forestAsArray = FOREST.toTypedArray<Tree>()
            }
        }
        
        /** Remove all planted trees.  */
        @SuppressWarnings("unused")
        fun uprootAll() {
            synchronized(FOREST) {
                FOREST.clear()
                forestAsArray = TREE_ARRAY_EMPTY
            }
        }
        
        /** Return a copy of all planted [trees][Tree].  */
        @SuppressWarnings("unused")
        fun forest(): List<Tree> {
            synchronized(FOREST) {
                return unmodifiableList(ArrayList(FOREST))
            }
        }
        
        @SuppressWarnings("unused")
        fun treeCount(): Int {
            synchronized(FOREST) {
                return FOREST.size
            }
        }
        
        private val TREE_ARRAY_EMPTY = arrayOf<Tree>()
        // Both fields guarded by 'FOREST'.
        private val FOREST = ArrayList<Tree>()
        @Volatile
        internal var forestAsArray = TREE_ARRAY_EMPTY
        
        /** A [Tree] that delegates to all planted trees in the [forest][.FOREST].  */
        private val TREE_OF_SOULS = object : Tree() {
            override fun v(message: String, vararg args: Any) {
                val forest = forestAsArray
                
                var i = 0
                val count = forest.size
                while (i < count) {
                    forest[i].v(message, *args)
                    i++
                }
            }
            
            override fun v(t: Throwable, message: String, vararg args: Any) {
                val forest = forestAsArray
                
                var i = 0
                val count = forest.size
                while (i < count) {
                    forest[i].v(t, message, *args)
                    i++
                }
            }
            
            override fun v(t: Throwable) {
                val forest = forestAsArray
                
                var i = 0
                val count = forest.size
                while (i < count) {
                    forest[i].v(t)
                    i++
                }
            }
            
            override fun d(message: String, vararg args: Any) {
                val forest = forestAsArray
                
                var i = 0
                val count = forest.size
                while (i < count) {
                    forest[i].d(message, *args)
                    i++
                }
            }
            
            override fun d(t: Throwable, message: String, vararg args: Any) {
                val forest = forestAsArray
                
                var i = 0
                val count = forest.size
                while (i < count) {
                    forest[i].d(t, message, *args)
                    i++
                }
            }
            
            override fun d(t: Throwable) {
                val forest = forestAsArray
                
                var i = 0
                val count = forest.size
                while (i < count) {
                    forest[i].d(t)
                    i++
                }
            }
            
            override fun i(message: String, vararg args: Any) {
                val forest = forestAsArray
                
                var i = 0
                val count = forest.size
                while (i < count) {
                    forest[i].i(message, *args)
                    i++
                }
            }
            
            override fun i(t: Throwable, message: String, vararg args: Any) {
                val forest = forestAsArray
                
                var i = 0
                val count = forest.size
                while (i < count) {
                    forest[i].i(t, message, *args)
                    i++
                }
            }
            
            override fun i(t: Throwable) {
                val forest = forestAsArray
                
                var i = 0
                val count = forest.size
                while (i < count) {
                    forest[i].i(t)
                    i++
                }
            }
            
            override fun w(message: String, vararg args: Any) {
                val forest = forestAsArray
                
                var i = 0
                val count = forest.size
                while (i < count) {
                    forest[i].w(message, *args)
                    i++
                }
            }
            
            override fun w(t: Throwable, message: String, vararg args: Any) {
                val forest = forestAsArray
                
                var i = 0
                val count = forest.size
                while (i < count) {
                    forest[i].w(t, message, *args)
                    i++
                }
            }
            
            override fun w(t: Throwable) {
                val forest = forestAsArray
                
                var i = 0
                val count = forest.size
                while (i < count) {
                    forest[i].w(t)
                    i++
                }
            }
            
            override fun e(message: String, vararg args: Any) {
                val forest = forestAsArray
                
                var i = 0
                val count = forest.size
                while (i < count) {
                    forest[i].e(message, *args)
                    i++
                }
            }
            
            override fun e(t: Throwable, message: String, vararg args: Any) {
                val forest = forestAsArray
                
                var i = 0
                val count = forest.size
                while (i < count) {
                    forest[i].e(t, message, *args)
                    i++
                }
            }
            
            override fun e(t: Throwable) {
                val forest = forestAsArray
                
                var i = 0
                val count = forest.size
                while (i < count) {
                    forest[i].e(t)
                    i++
                }
            }
            
            override fun wtf(message: String, vararg args: Any) {
                val forest = forestAsArray
                
                var i = 0
                val count = forest.size
                while (i < count) {
                    forest[i].wtf(message, *args)
                    i++
                }
            }
            
            override fun wtf(t: Throwable, message: String, vararg args: Any) {
                val forest = forestAsArray
                
                var i = 0
                val count = forest.size
                while (i < count) {
                    forest[i].wtf(t, message, *args)
                    i++
                }
            }
            
            override fun wtf(t: Throwable) {
                val forest = forestAsArray
                
                var i = 0
                val count = forest.size
                while (i < count) {
                    forest[i].wtf(t)
                    i++
                }
            }
            
            override fun log(priority: Int, message: String, vararg args: Any) {
                val forest = forestAsArray
                
                var i = 0
                val count = forest.size
                while (i < count) {
                    forest[i].log(priority, message, *args)
                    i++
                }
            }
            
            override fun log(priority: Int, t: Throwable, message: String, vararg args: Any) {
                val forest = forestAsArray
                
                var i = 0
                val count = forest.size
                while (i < count) {
                    forest[i].log(priority, t, message, *args)
                    i++
                }
            }
            
            override fun log(priority: Int, t: Throwable) {
                val forest = forestAsArray
                
                var i = 0
                val count = forest.size
                while (i < count) {
                    forest[i].log(priority, t)
                    i++
                }
            }
            
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                throw AssertionError("Missing override for log method.")
            }
        }
    }
}
