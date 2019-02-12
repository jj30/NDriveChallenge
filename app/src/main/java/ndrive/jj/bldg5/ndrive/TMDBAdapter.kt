package ndrive.jj.bldg5.ndrive

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.movie_row.view.*
import org.joda.time.DateTime


interface MoviesRecyclerClickListener {
    fun onPositionClicked(position: Int)
}

class TMDBAdapter(val items : List<Movie>,
                  private val rowListener: MoviesRecyclerClickListener,
                  val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val file_name = items.get(position).poster_path ?: ""

        // Download image using picasso library
        if (file_name.isNotEmpty()) {
            val uri = Uri.parse("https://image.tmdb.org/t/p/w500/$file_name")
            Picasso.with(context).load(uri)
                    .into(holder.itemView.posterImage)
        }

        try {
            val nYear = DateTime(items.get(position).release_date).year
            val currentYear = DateTime().year
            holder.itemView.textTitle.text = items.get(position).title
            holder.itemView.textYear.text = nYear.toString()

            if (nYear == currentYear) {
                holder.itemView.textYear.setTextColor(Color.RED)
                holder.itemView.textYear.setTypeface(null, Typeface.BOLD)
            }
        } catch(ex: Exception) {
            // Bad data, ie. empty date.
            Log.e(javaClass.name, "An error was encountered: ${ex.message}")
        }

        holder.itemView.setOnClickListener {
            rowListener.onPositionClicked(position)
        }
    }

    class ViewHolder(val rowView: LinearLayout) : RecyclerView.ViewHolder(rowView)

    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return ViewHolder(layoutInflater.inflate(R.layout.movie_row, parent, false) as LinearLayout)
    }
}