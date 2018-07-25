package ${PACKAGE_NAME}

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.os.Environment
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@Suppress("unused")
class ImageUtil {

    fun decodeBitmapFromResource(path: String): Bitmap? {
        try {
            if (StringUtils.isNull(path)) {
                return null
            }

            // First decode with inJustDecodeBounds=true to check dimensions
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(path, options)

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, WIDTH_SIZE_IN_PIXELS, HEIGHT_SIZE_IN_PIXELS)

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false
            val image = BitmapFactory.decodeFile(path, options)

            // Rotate image
            val matrix = Matrix()
            matrix.postRotate(getImageOrientation(path).toFloat())

            return Bitmap.createBitmap(image, 0, 0, image.width, image.height, matrix, true)
        } catch (e: Exception) {
            Log.e(ImageUtil::class.java.simpleName,
                    String.format("Error decoding bitmap from path: %s", path), e)
            return null
        }

    }

    companion object {
        private val HEIGHT_SIZE_IN_PIXELS = 300
        private val WIDTH_SIZE_IN_PIXELS = 300

        val GOOGLE_PHOTOS_CONTENT = "content://com.google.android.apps.photos.content"

        val ROOT_PATH =
                Environment.getExternalStorageDirectory().toString() + File.separator + "Patbook" + File.separator
        val TEMP_PATH = ROOT_PATH + "temp" + File.separator
        val JPG = ".jpg"
        val DATE_FORMAT = "yyyyMMdd_HHmmss"

        val BITMAP_ENCODED_QUALITY = 50
        val BITMAP_DECODED_SIZE = 500

        fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int,
                                  reqHeight: Int): Int {
            // Raw height and width of image
            val height = options.outHeight
            val width = options.outWidth
            var inSampleSize = 1

            if (height > reqHeight || width > reqWidth) {

                val halfHeight = height / 2
                val halfWidth = width / 2

                // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                // height and width larger than the requested height and width.
                while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                    inSampleSize *= 2
                }
            }

            return inSampleSize
        }

        fun decodeBitmap(encodedImage: String, reqWidth: Int, reqHeight: Int): Bitmap {

            val image: Bitmap
            val decodedString = Base64.decode(encodedImage, Base64.DEFAULT)

            // First decode with inJustDecodeBounds=true to check dimensions
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size, options)

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false
            image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size, options)

            return image
        }

        fun decodeBitmapFromResource(path: String, reqWidth: Int, reqHeight: Int): Bitmap {

            val image: Bitmap

            // First decode with inJustDecodeBounds=true to check dimensions
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(path, options)

            // Calculate inSampleSize
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)

            // Decode bitmap with inSampleSize set
            options.inJustDecodeBounds = false
            image = BitmapFactory.decodeFile(path, options)

            /* Only if bitmap is in local memory (info is in metadata) */
            return rotateBitmap(path, image)
        }

        fun deleteDirectory(path: String): Boolean {
            return deleteDirectory(File(path))
        }

        fun deleteDirectory(path: File): Boolean {
            if (path.exists()) {
                val files = path.listFiles() ?: return true
                for (file in files) {
                    if (file.isDirectory) {
                        deleteDirectory(file)
                    } else {
                        file.delete()
                    }
                }
            }
            return path.delete()
        }

        fun encodeBitmap(image: Bitmap, quality: Int): String {
            val outputStream = ByteArrayOutputStream()
            image.compress(Bitmap.CompressFormat.JPEG, quality, outputStream)
            val byteArrayImage = outputStream.toByteArray()
            return Base64.encodeToString(byteArrayImage, Base64.DEFAULT)
        }

        fun getImageOrientation(imagePath: String): Int {
            var rotate = 0
            try {
                val imageFile = File(imagePath)
                val exif = ExifInterface(imageFile.absolutePath)
                val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_NORMAL)

                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_270 -> rotate = 270
                    ExifInterface.ORIENTATION_ROTATE_180 -> rotate = 180
                    ExifInterface.ORIENTATION_ROTATE_90 -> rotate = 90
                    else -> {
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return rotate
        }

        fun rotateBitmap(path: String, image: Bitmap): Bitmap {
            val matrix = Matrix()
            matrix.postRotate(getImageOrientation(path).toFloat())
            return Bitmap.createBitmap(image, 0, 0, image.width, image.height, matrix, true)
        }

        fun saveBitmap(path: String, bitmap: Bitmap) {
            var out: FileOutputStream? = null
            try {
                out = FileOutputStream(path)
                bitmap.compress(Bitmap.CompressFormat.PNG, BITMAP_ENCODED_QUALITY, out)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    if (out != null) {
                        out.close()
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }

        /**
         * Adds .nomedia file in order to hide generated videos in gallery
         * @param directory The directory path to add the .nomedia file
         */
        fun addNomediaFile(directory: File) {
            try {
                if (directory.exists()) {
                    val nomediaFile = File(directory, ".nomedia")
                    nomediaFile.createNewFile()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
