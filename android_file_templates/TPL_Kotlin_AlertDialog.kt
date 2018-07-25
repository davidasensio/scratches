AlertDialog.Builder(this)
        .setCancelable(false)
        .setTitle("${TITLE}")
        .setMessage("${MESSAGE}")
        .setPositiveButton(android.R.string.ok) { dialog, _ ->
            dialog?.dismiss()
            finish()
        }.setNegativeButton(android.R.string.cancel) { dialog, _ ->
            dialog?.dismiss()
        }
        .show()