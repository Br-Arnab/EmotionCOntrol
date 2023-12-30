package com.saradaedu.emotioncontrol

import android.os.Bundle
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.internal.Storage
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class Bookviewmodel :ViewModel() {

    private  var  _books= mutableStateOf<List<Book>>(emptyList())
    private  var _topics= mutableStateOf<List<Emotions>>(emptyList())
    private  var _authors= mutableStateOf<List<Authors>>(emptyList())
    private  var _images= mutableStateOf<List<Images>>(emptyList())
    val images:State<List<Images>>
        get() = _images
    val books : State<List<Book>>
        get() = _books
    val topics : State<List<Emotions>>
        get() = _topics
    val authors : State<List<Authors>>
      get() = _authors
    private  val  db =Firebase.firestore
    private  val imgst=  FirebaseStorage.getInstance()
    private var query= db.collection("Emotions")
    val querystring = mutableStateOf("Swami Brahmananda")  // initial query value
    val authorstring= mutableStateOf("")
    val querystring2 = mutableStateOf("Sorrow")

    init {
        query.addSnapshotListener { value, error ->
            if (value!=null)
            {
                _topics.value=value.toObjects()
            }
        }

    }
    fun getimages(){
        /*
        var query:String
        var url= FirebaseStorage.getInstance().getReferenceFromUrl("gs://emotion-control.appspot.com/")
         url.downloadUrl.addOnSuccessListener {

         }
         */

        var query=db.collection("Images")
        query.addSnapshotListener { value, error ->
            if (value!=null)
            {
                _images.value=value.toObjects()

            }
        }


    }

  fun topicupdate() {
     var query= db.collection("Emotions")
      query.addSnapshotListener { value, error ->
          if (value!=null)
          {
              _topics.value=value.toObjects()
          }
      }
  }
    fun gettopic(query: String)
    {
       var query=db.collection("Book").whereEqualTo("topic",query)
        query.addSnapshotListener { value, error ->
            if (value!=null)
            {
                _books.value=value.toObjects()
                topicupdate()
            }
        }
    }
    fun getauthors (query: String)
    {
        var query=db.collection("Authors")
        query.addSnapshotListener { value, error ->
            if (value!=null)
            {
                _authors.value=value.toObjects()
                //topicupdate()
            }
        }
    }
    fun getauthors2 (query: String)
    {
        var query=db.collection("Book").whereEqualTo("author",query)
        query.addSnapshotListener { value, error ->
            if (value!=null)
            {
                _books.value=value.toObjects()
                //topicupdate()
            }
        }
    }
    fun onquerychange (query: String){
        querystring.value=query.toString()
    }
     fun getquerystring(): String {
     return querystring.value
    }
    fun onquerychange2 (query: String){
        querystring2.value=query.toString()
    }
    fun getquerystring2(): String {
        return querystring2.value
    }

}