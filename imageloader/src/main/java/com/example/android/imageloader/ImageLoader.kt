import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.util.LruCache
import android.widget.ImageView
import com.example.android.imageloader.R
import java.lang.ref.WeakReference
import java.net.HttpURLConnection
import java.net.URL
import java.util.concurrent.Executors

class ImageLoader private constructor() {

    companion object {

        @Volatile
        private var instance: ImageLoader? = null

        fun getInstance(): ImageLoader? {
            if (instance == null) {
                synchronized(ImageLoader::class.java) {
                    if (instance == null) {
                        instance = ImageLoader()
                    }
                }
            }

            return instance
        }
    }

    private val cache: LruCache<String, Bitmap>
    private var executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())
    private val handler = Handler(Looper.getMainLooper())

    init {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxMemory / 8

        this.cache = object : LruCache<String, Bitmap>(cacheSize) {
            override fun sizeOf(key: String, bitmap: Bitmap): Int {
                return bitmap.byteCount / 1024
            }
        }
    }

    fun load(imageUrl: String?, weakReference: WeakReference<ImageView>) {
        val imageView = weakReference.get()

        if (imageUrl != null) {
            val currentUrl = imageView?.getTag(R.id.IMAGE_TAG_URL) as String?

            imageView?.setTag(R.id.IMAGE_TAG_URL, imageUrl)

            val bitmap = cache.get(imageUrl)

            if (bitmap != null && imageView != null) {
                updateImageView(imageView, bitmap)
                return
            }

            if (currentUrl != imageUrl || imageView?.drawable == null) {
                imageView?.setImageResource(R.drawable.image_placeholder)
            }

            executor.submit {
                val image: Bitmap? = downloadImage(imageUrl)

                if (image != null && imageView?.getTag(R.id.IMAGE_TAG_URL) == imageUrl) {
                    updateImageView(imageView, image)
                    cache.put(imageUrl, image)
                }
            }
        } else {
            imageView?.setImageResource(R.drawable.image_placeholder)
        }
    }

    private fun updateImageView(imageView: ImageView, bitmap: Bitmap) {
        handler.post {
            imageView.background = null
            imageView.setImageBitmap(bitmap)
        }
    }

    private fun downloadImage(imageUrl: String): Bitmap? {
        var bitmap: Bitmap? = null

        try {
            val url = URL(imageUrl)
            val connection = url.openConnection() as HttpURLConnection

            bitmap = BitmapFactory.decodeStream(connection.inputStream)

            connection.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return bitmap
    }
}