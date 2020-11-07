package com.example.burgerapp


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_view.*
import kotlinx.android.synthetic.main.item_view.view.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener {
    companion object {
        val name_item = "name_item"
        val desription_item = "desription_item"

        val image_id = "image_id"
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

        uploadList()

    }

    var itemArray = ArrayList<ItemList>()


    fun uploadList() {
        var secondArrayList = ArrayList<ItemList>()

        if (checkBox_Burger.isChecked) {
            secondArrayList.addAll(itemArray.filter {
                it.type == "Burger"
            })
        }
        if (checkBox_Drink.isChecked) {
            secondArrayList.addAll(itemArray.filter {
                it.type == "Drink"
            })
        }
        if (checkBox_Snack.isChecked) {
            secondArrayList.addAll(itemArray.filter {
                it.type == "Snack"
            })
        }
//spinner for the new selected itemArray
        if (type_spinner.selectedItemPosition == 0) {
            secondArrayList.sortBy {
                it.type
            }
        } else if (type_spinner.selectedItemPosition == 1) {
            secondArrayList.sortBy {
                it.name
            }
        } else if (type_spinner.selectedItemPosition == 2) {
            secondArrayList.sortBy {
                it.calorie
            }
        }

        //notifying the changed in the arraylist to the recyclerviewadapter
        viewAdapter.foodData = secondArrayList.toTypedArray()
        viewAdapter.notifyDataSetChanged()

    }

//call uploast method
    override fun onClick(p0: View?) {
        uploadList()
    }

    /**
     * read the CSV data
     * */
    fun loadData() {
        //DATA STRING REPRESENt the entire data of csv file
        val dataString =
            resources.openRawResource(R.raw.mcdonalds).bufferedReader()
                .use { it.readText() }// read the entire file as a string

        var lines = dataString.trim().split("\n") // split each line
        lines = lines.subList(1, lines.size) // get rid of the header line
        //Add to the stock Array.
        lines.forEach {
            itemArray.add(makefood(it))
        }
    }

    /**
     * Split one line from CSV file and create the food object
     * @param line :
     */
    fun makefood(line: String): ItemList {

        val cells = line.split(";")
        //passing sector, name, price, ..... values from macdonalds csv file
        val drawableResourcID = resources.getIdentifier(cells[3], "drawable", this.packageName)
        val item = ItemList(
            cells[0],
            cells[1],
            cells[2].toInt(),
            drawableResourcID,

            cells[4]
        )
        return item
    }


    lateinit var viewAdapter: RecyclerViewAdapter
    lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadData()


        val sectorArray = arrayOf("Type", "Name", "Calorie")
        //sectorArray: [Type, Name and Calories and sorts by ascending order]
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            sectorArray
        )

        type_spinner.adapter = adapter
        type_spinner.onItemSelectedListener = this


        viewManager = LinearLayoutManager(this)
        food_recycler.layoutManager = viewManager
        viewAdapter =
            RecyclerViewAdapter(itemArray.toTypedArray()) //converting it to the array list
        food_recycler.adapter = viewAdapter
        viewAdapter.clickLamda = itemClickLambda
        checkBox_Burger.setOnClickListener(this)
        checkBox_Snack.setOnClickListener(this)
        checkBox_Drink.setOnClickListener(this)

        btn_Calculate.setOnClickListener {

            val total =
                itemArray.sumBy {
                    if (it.selectedFood) {
                        it.calorie
                    } else {
                        0
                    }

                }

            val toast = Toast.makeText(this, "The total calories is ${total} ", Toast.LENGTH_LONG)
            toast.show()

        }


    }


    val itemClickLambda: (ItemList) -> Unit = {

        val intent =
            Intent(this, DetailActivity::class.java)// from this activity to mainactivity class
        //going back to first one
        intent.putExtra(name_item, it.name)
        intent.putExtra(desription_item, it.description)
        intent.putExtra(image_id, it.imageid)

        startActivity(intent)

    }


}