package com.example.oliverecipe.navigation.view


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.oliverecipe.R
import com.example.oliverecipe.navigation.API.Row
import kotlinx.android.synthetic.main.alist_item.view.*
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage

var favoriteName : String? = null

class oliveListAdapter (val rows: List<Row>)
    : RecyclerView.Adapter<oliveListAdapter.ViewHolder>() {


    inner class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(row: Row) {
            val rName: TextView = itemView.findViewById(R.id.rName)
            rName.text = row.rCPNM

            Glide.with(itemView).load(row.aTTFILENOMK).into(itemView.imageView13)

            val rIngredient: TextView = itemView.findViewById(R.id.rIngredient)
            rIngredient.text = row.rCPPARTSDTLS

            Glide.with(itemView).load(row.aTTFILENOMAIN).into(itemView.imageViewM)

            val reName: TextView = itemView.findViewById(R.id.reName)
            reName.text = row.rCPNM
            Glide.with(itemView).load(row.mANUALIMG01).into(itemView.imageView)
            val howto: TextView = itemView.findViewById(R.id.howto)
            howto.text = row.mANUAL01
            Glide.with(itemView).load(row.mANUALIMG02).into(itemView.imageView2)
            val howto2: TextView = itemView.findViewById(R.id.howto2)
            howto2.text = row.mANUAL02
            Glide.with(itemView).load(row.mANUALIMG03).into(itemView.imageView3)
            val howto3: TextView = itemView.findViewById(R.id.howto3)
            howto3.text = row.mANUAL03
            Glide.with(itemView).load(row.mANUALIMG04).into(itemView.imageView4)
            val howto4: TextView = itemView.findViewById(R.id.howto4)
            howto4.text = row.mANUAL04
            Glide.with(itemView).load(row.mANUALIMG05).into(itemView.imageView5)
            val howto5: TextView = itemView.findViewById(R.id.howto5)
            howto5.text = row.mANUAL05
            Glide.with(itemView).load(row.mANUALIMG06).into(itemView.imageView5)
            val howto6: TextView = itemView.findViewById(R.id.howto6)
            howto6.text = row.mANUAL06
            Glide.with(itemView).load(row.mANUALIMG07).into(itemView.imageView5)
            val howto7: TextView = itemView.findViewById(R.id.howto7)
            howto7.text = row.mANUAL07
            Glide.with(itemView).load(row.mANUALIMG08).into(itemView.imageView5)
            val howto8: TextView = itemView.findViewById(R.id.howto8)
            howto8.text = row.mANUAL08
            Glide.with(itemView).load(row.mANUALIMG09).into(itemView.imageView5)
            val howto9: TextView = itemView.findViewById(R.id.howto9)
            howto9.text = row.mANUAL09
            Glide.with(itemView).load(row.mANUALIMG10).into(itemView.imageView5)
            val howto10: TextView = itemView.findViewById(R.id.howto10)
            howto10.text = row.mANUAL10
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType:Int): ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(R.layout.alist_item, parent,false)

        view.listCard.setOnClickListener{
            view.card.visibility = View.VISIBLE
            view.listCard.visibility = View.GONE
        }

        view.card.setOnClickListener{
            view.card.visibility = View.GONE
            view.listCard.visibility = View.VISIBLE
        }

        view.favorite.setOnClickListener{
            favoriteName = view.reName.text.toString()
            Toast.makeText(view.context, "북마크에 추가했습니다", Toast.LENGTH_SHORT).show()
        }

        view.btn_sound.setOnClickListener {
            try {
                val client =
                    MqttClient("tcp://172.30.1.21:1883", MqttClient.generateClientId(), null)
                client.connect()
                client.publish("data", MqttMessage("오늘의 추천 레시피는 ${view.reName.text}입니다. 필요 재료는 ${view.rIngredient.text}입니다.".toByteArray()))
            } catch (e: MqttException) {
                e.printStackTrace()
            }
        }

        //MQTT publish를 여기서 한다. -> 라즈베리 파이에서 서브스크라이브

        return ViewHolder(view)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = rows[position]
        holder.bind(data)
    }
    override fun getItemCount(): Int = rows.size }