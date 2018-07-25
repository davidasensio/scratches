package ${PACKAGE_NAME}
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.develapps.baseproject.R
import com.develapps.baseproject.common.BaseViewModelPaginated
#parse("File Header.java")
class ${NAME}(val context: Context, val viewModel: BaseViewModelPaginated) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var ${NAME}dataList: Array<${ITEM_TYPE}> = arrayOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(${LAYOUT}, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val dataItem: ${ITEM_TYPE} = dataList[position]
        (holder as ViewHolder).bindTo(dataItem)
    }

    internal inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        init {
            /*view.deleteButton.setOnClickListener {
                viewModel.delete(dataList[adapterPosition])
                notifyDataSetChanged()
            }*/
        }

        fun bindTo(dataItem: ${ITEM_TYPE}) {
            //itemView.fieldTextView.text = dataItem.field
            viewModel.getNextPage(adapterPosition)
        }
    }
}