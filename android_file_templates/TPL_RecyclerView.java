package ${PACKAGE_NAME};
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
#parse("File Header.java")
public class ${NAME} extends RecyclerView.Adapter<${NAME}.${VIEW_HOLDER_CLASS}>{
private List<${ITEM_TYPE}> dataList = new ArrayList<>();
@Override
public ${VIEW_HOLDER_CLASS} onCreateViewHolder(ViewGroup parent, int viewType) {
return new ${VIEW_HOLDER_CLASS}(LayoutInflater.from(parent.getContext()).
inflate(${LAYOUT}, parent, false));
}
@Override
public void onBindViewHolder(${NAME}.${VIEW_HOLDER_CLASS} holder, int position) {
final ${ITEM_TYPE} dataItem = dataList.get(position);
holder.bindTo(dataItem);
}
@Override
public int getItemCount() {
return dataList.size();
}
public void addItems(List<${ITEM_TYPE}> dataList){
this.dataList.addAll(dataList);
notifyDataSetChanged();
}
class ${VIEW_HOLDER_CLASS} extends RecyclerView.ViewHolder {
${VIEW_HOLDER_CLASS}(View itemView) {
super(itemView);
}
void bindTo(${ITEM_TYPE} dataItem) {
    //itemView.getData().setText(dataItem)
}
}
}