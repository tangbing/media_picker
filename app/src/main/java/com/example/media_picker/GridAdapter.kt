import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.media_picker.R


class GridAdapter(
    private var contentList: MutableList<Uri>,
    private val maxCount: Int,
    private val onAddClick: () -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // view 就是 item_grid.xml
        // 从这个 view 里找到 img 和 iv_delete
        val image: ImageView = view.findViewById(R.id.img)
        val deleteBtn: ImageView = view.findViewById(R.id.iv_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        /// 1.把 item_grid.xml 加载成 View 对象
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_grid, parent, false)
        /// 2. 把这个 view 传给 ViewHolder
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (contentList.size < maxCount) contentList.size + 1 else contentList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val vh = holder as ViewHolder
        val isAddButton = position == contentList.size && contentList.size < maxCount

        if (isAddButton) {
            vh.image.setImageResource(android.R.drawable.ic_input_add)
            vh.image.scaleType = ImageView.ScaleType.CENTER
            vh.deleteBtn.visibility = View.GONE
            vh.itemView.setOnClickListener { onAddClick() }

        } else {
            vh.image.setImageURI(contentList[position])
            vh.image.scaleType = ImageView.ScaleType.CENTER_CROP
            vh.deleteBtn.visibility = View.VISIBLE
            vh.deleteBtn.setOnClickListener {
                contentList.removeAt (position)
                notifyDataSetChanged()
            }
            vh.itemView.setOnClickListener(null)
        }
    }

}
