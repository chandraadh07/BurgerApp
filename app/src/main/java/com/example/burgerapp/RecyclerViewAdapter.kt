package com.example.burgerapp



import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_detail.view.*
import kotlinx.android.synthetic.main.item_view.*
import kotlinx.android.synthetic.main.item_view.view.*
import kotlinx.android.synthetic.main.item_view.view.btn_selectItem

class RecyclerViewAdapter (var foodData:Array<ItemList>):
    RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        //inflate (convert a text file to visual componennt) xml from stock into an object

        val viewItem= LayoutInflater.from(parent.context).inflate(
            R.layout.item_view,
            parent,false
        )
        return RecyclerViewHolder(viewItem)
    }

    override fun getItemCount(): Int {
        return foodData.size

        //pass secondlist to fooddata
    }

    lateinit var clickLamda: (ItemList) -> Unit

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bind(foodData[position],clickLamda)
    }

///
    class RecyclerViewHolder(val viewItem: View): RecyclerView.ViewHolder(viewItem){
        fun bind(item: ItemList, clickLamda: (ItemList)->Unit){
            viewItem.findViewById<TextView>(R.id.name_text).text=item.name
            viewItem.findViewById<TextView>(R.id.calorie_amount).text=item.calorie.toString()
            viewItem.profile_image.setImageResource(item.imageid)
            //
            viewItem.btn_selectItem.isChecked=item.selectedFood
            viewItem.btn_selectItem.setOnClickListener{

                //to get the selected value from the checkbox
                item.selectedFood= (it as CheckBox).isChecked

            }
            //
            viewItem.setOnClickListener{
                clickLamda(item)
            }
        }


    }


}