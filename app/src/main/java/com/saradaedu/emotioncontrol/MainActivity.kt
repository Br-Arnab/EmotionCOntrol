package com.saradaedu.emotioncontrol

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.Icons.Filled
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType

import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.saradaedu.emotioncontrol.R

import com.saradaedu.emotioncontrol.ui.theme.EmotionControlTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

   // private val viewModel by viewModels<Bookviewmodel>()
    @ExperimentalMaterialApi
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       //MobileAds.initialize(this) {}

       //checkupdate(this)
        setContent {
            val mp : MediaPlayer = MediaPlayer.create(this, R.raw.mediom)
            mp.start()

            EmotionControlTheme {
                val navController = rememberNavController()

              //Greeting(onContinueClicked = {shouldShowOnboarding=false},visible,visible2,visible3,visible4,visible5 )
                Scaffold(
                    bottomBar = {
                        BottomNavigationBar(
                            items = listOf(
                                BottomNavItem(
                                    name = "Search",
                                    route = "search",
                                    icon = Icons.Default.Search
                                ),
                                BottomNavItem(
                                    name = "Home",
                                    route = "home",
                                    icon = Icons.Default.Home,
                                    badgeCount = 0
                                ),

                                BottomNavItem(
                                    name = "Music",
                                    route = "music",
                                    icon = Icons.Default.Star ,
                                    badgeCount = 0
                                ),
                            ),
                            navController = navController,
                            onItemClick = {
                                navController.navigate(it.route)
                            }
                        )
                    }
                ) {
                    Navigation(navController = navController)
                }


            }
        }
       //initialize the mobile ads sdk
       MobileAds.initialize(this) {}
       //load the interstitial ad
       loadInterstitial(this)
       //add the interstitial ad callbacks
       addInterstitialCallbacks(this)
    }

    @ExperimentalAnimationApi
    @Composable
    fun bookdisp(viewModel: Bookviewmodel, search: String) {
        LazyColumn() {
            viewModel.gettopic(search)
            items(viewModel.books.value) { book ->
                bookcard(book = book)

            }

        }


    }
    @ExperimentalAnimationApi
    @Composable
    fun bookdisp2(viewModel: Bookviewmodel, search: String) {


        LazyRow() {
            viewModel.getauthors2(search)
            items(viewModel.books.value)
            { book ->
                bookcard2(book = book)

            }
        }


        }






    @ExperimentalAnimationApi
    @Composable
    fun Navigation(navController: NavHostController) {
        NavHost(navController = navController, startDestination = "splash") {

            composable("home") {


                HomeScreen( )
            }
            composable("search") {
                SearchScreen()
            }
            composable("music") {
                MusicsScreen()
            }
            composable("splash") {
                Greeting(navController)
            }
        }
    }

    @ExperimentalAnimationApi
    @Composable
    fun HomeScreen() {

         var expanded by remember {
             mutableStateOf(true)
         }


//        Column( modifier = Modifier.clickable(onClick = {
//            expanded = !expanded
//
//        }))
        Column(){
            val context = LocalContext.current
           // loadInterstitial(context)
           // showInterstitial(context)
                    HomeScreen_2(expanded)
//                     IconButton(onClick = { expanded = !expanded }) {
//                     Icon(
//                    imageVector = if (expanded) Filled.KeyboardArrowUp else Filled.KeyboardArrowDown,
//                    contentDescription = if (expanded) {
//                        "Show Less";
//                    } else {
//                        "Show More"
//                    }
//                )
//
//            }
            //HomeScreen_1()

                 }




        }
   @Composable
   fun topic_head (head :String)
   {


           Spacer(modifier = Modifier.padding(vertical = 10.dp))
           Box(
               modifier = Modifier
                   .clip(RoundedCornerShape(topEnd = 24.dp))
                   .padding(4.dp)
                   .background(
                       Color.Red
                   )
           ) {
               Text(
                   text = "$head ... ",
                   Modifier.padding(8.dp),
                   color = MaterialTheme.colors.onPrimary,
                   fontFamily = FontFamily.Cursive,
                   fontStyle = FontStyle.Normal,
                   fontSize = 16.sp
               )
           }

   }
    @Composable
    fun loadimage(url:  String) {

        Card( border = BorderStroke(1.dp, Color.Black), elevation = 8.dp,
            modifier= Modifier
                .padding(12.dp)
                .fillMaxWidth(.4f)
                .fillMaxHeight(.6f),
            shape = MaterialTheme.shapes.small.copy(
                topStart= CornerSize(30 .dp),
                bottomStart = CornerSize(0 .dp), // overrides small theme style
                bottomEnd = ZeroCornerSize // overrides small theme style
            )) {
           val image = loadPicture(url = url
                 , defaultImage = R.drawable.ic_launcher_background ).value
                 image?.let { img ->
                                     Image(bitmap =img.asImageBitmap() ,
                                         modifier = Modifier.fillMaxWidth(),
                                         contentDescription = "News",

                                         contentScale = ContentScale.Fit)

                 }


            /*
            Image(
                painter = rememberImagePainter(url),
                contentDescription = null,
                modifier = Modifier.size(128.dp)   ,
                contentScale = ContentScale.Fit
            )

            Column(modifier = Modifier.padding(10.dp)) {
                Text("AB CDE", fontWeight = FontWeight.W700)
                Text("+0 12345678")
                Text("XYZ city", fontWeight = FontWeight.W300)
            }
            */


        }




    }
    @Composable
    fun
            HomeScreen_1() {
        val viewModel by viewModels<Bookviewmodel>()
        viewModel.getimages()
        topic_head(head = "News Room")
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .horizontalScroll(rememberScrollState())
        ) {
            //  var url="https://firebasestorage.googleapis.com/v0/b/emotion-control.appspot.com/o/recent.png?alt=media&token=365c4f24-952c-40a7-93d3-0943be6bcf5f"


            Spacer(modifier = Modifier.padding(vertical = 5.dp))
            Row() {

                for (i in viewModel.images.value) {
                    loadimage(url = i.link.toString())
                }
            }
        }


    }


    @ExperimentalAnimationApi
    @Composable
    fun HomeScreen_2(show: Boolean) {

        val viewModel by viewModels<Bookviewmodel>()

        //var showDialog by remember { mutableStateOf(true) }

        var search = viewModel.authorstring.value

       Column(modifier = Modifier
           .fillMaxWidth()
           .verticalScroll(rememberScrollState())
       ) {

                Spacer(modifier = Modifier.padding(vertical = 10.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topEnd = 24.dp))
                        .padding(4.dp)
                        .background(
                            Color.Red
                        )
                ) {
                    Text(
                        text = "Teachings of Gods and their Disciples... ",
                        Modifier.padding(8.dp),
                        color = MaterialTheme.colors.onPrimary,
                        fontFamily = FontFamily.Cursive,
                        fontStyle = FontStyle.Normal,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.padding(vertical = 5.dp))

                LazyRow(
                    modifier = Modifier
                        .padding(12.dp)


                    ) {

                        viewModel.getauthors(search)
                        items( viewModel.authors.value) { author->
                           card_auth(author =author,viewModel)


                    }
                }

                if(show)
                bookdisp2(viewModel = viewModel, search =viewModel.getquerystring()) // for the authors


           val adWidth = LocalConfiguration.current.screenWidthDp - 32
           val context = LocalContext.current
           AndroidView(
               factory = { context ->
                   AdView(context).apply {this.setAdSize(AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(context,adWidth))
                       adUnitId = context.getString(R.string.ad_id_banner)
                       loadAd(AdRequest.Builder().build())
                   }
               }
           )



       }
    }
    @SuppressLint("UnrememberedMutableState")
    @ExperimentalAnimationApi
    @Composable
    fun card_auth(author: Authors, viewModel: Bookviewmodel, ) {


            Card(
                border = BorderStroke(1.dp, Color.Black), elevation = 8.dp,
                modifier = Modifier
                    .padding(1.dp)

                    .clickable(
                        onClick = {
                            viewModel.onquerychange(author.author)

                            Log.v("final", "clicked")
                            //showme

                        }
                    ),
            )
            {
                Column() {
                    Image(
                        //painter = painterResource(id = R.drawable.swamiji),
                        painter = painterResource(getid(author.author)),
                        contentDescription = "Swamiji",
                        modifier = Modifier
                            // Set image size to 40 dp
                            .size(140.dp)
                            // Clip image to be shaped as a circle
                            .clip(RoundedCornerShape(90))
                            .padding(all = 10.dp)

                            .clickable(onClick = {
                                viewModel.onquerychange(author.author)

                                Log.v("final", "clicked")

                                //Unit
                            })


                    )

                    Row(horizontalArrangement = Arrangement.End,modifier = Modifier.fillMaxWidth()) {

                        Text(
                            text = if (author.author == "Mother") "Holy " + author.author else if (author.author == "Ramakrishna") "Sri " + author.author else author.author,
                            modifier = Modifier
                                .padding(10.dp)
                                .padding(top = 1.dp)
                                .padding(horizontal = 20.dp)
                                ,

                            fontSize = 12.sp,
                            fontStyle = FontStyle.Normal,
                            fontFamily = FontFamily.Serif
                        )


                    }
                }

            }

    }
    @Composable
    fun FavoriteButton(onClick: () -> Unit) {
        IconButton(onClick) {
            Icon(
                imageVector = Icons.Filled.ThumbUp,
                contentDescription = "ahri"
            )
        }
    }
    @Composable
    private fun FunctionalityNotAvailablePopup(onDismiss: () -> Unit) {
        AlertDialog(
            onDismissRequest = onDismiss,
            text = {
                Text(
                    text = " v",
                    style = MaterialTheme.typography.body2
                )
            },
            confirmButton = {
                TextButton(onClick = onDismiss) {
                    Text(text = "ljwehg")
                }
            }
        )
    }
    @ExperimentalAnimationApi
    @Composable
    fun SearchScreen() {
        val mp : MediaPlayer = MediaPlayer.create(this, R.raw.mediom)
        mp.start()
        val viewModel by viewModels<Bookviewmodel>()
        var search = viewModel.querystring2.value
        Column() {

            OutlinedTextField(
                value = search,
                onValueChange = { newval ->
                    viewModel.onquerychange2(newval)


                },
                label = { Text(text = "Tell us your Emotion") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    //.onGloballyPositioned { coordinates ->
                        //This value is used to assign to the DropDown the same width
                       // textfieldSize = coordinates.size.toSize()
                    //}
                        ,
                shape = RoundedCornerShape(8.dp),
                leadingIcon =  {
                    Icon(Icons.Filled.Search, "", tint = Color.Blue)
                    /*Icon(
                        icon,
                        "contentDescription",
                        Modifier.clickable { expanded = !expanded },
                        tint = Color.Blue
                    )
                    */

                },
                keyboardOptions = KeyboardOptions(
                    KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                //keyboardActions = KeyboardActions (onSearch ={shouldShowOnboarding=true} )
            )

            Row(modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(
                    rememberScrollState()
                )
                .background(MaterialTheme.colors.surface)

            ) {
                for ( i in viewModel.topics.value) {
                    Card(
                        modifier = Modifier.padding(8.dp)


                            ,shape= CircleShape
                    )
                    {
                        Text(
                            text = i.topic, modifier = Modifier
                                .clickable(onClick = { viewModel.onquerychange2(i.topic) })
                                .background(Color.White)
                                .padding(10.dp), fontSize = 15 .sp, fontFamily = FontFamily.Cursive, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colors.primary

                        )
                    }
                }

            }
            bookdisp(viewModel = viewModel, search = search)


            //bookdisp(viewModel = viewModel, search =search )

        }
    }
    @ExperimentalMaterialApi
    @Composable
    fun BottomNavigationBar(
        items: List<BottomNavItem>,
        navController: NavController,
        modifier: Modifier = Modifier,
        onItemClick: (BottomNavItem) -> Unit
    ) {
        val backStackEntry = navController.currentBackStackEntryAsState()
        BottomNavigation(
            modifier = modifier,
            backgroundColor = MaterialTheme.colors.primary,
            elevation = 0.dp
        ) {
            items.forEach { item ->
                val selected = item.route == backStackEntry.value?.destination?.route
                BottomNavigationItem(
                    selected = selected,
                    onClick = { onItemClick(item) },
                    selectedContentColor = Color.Green,
                    unselectedContentColor = Color.Gray,
                    icon = {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            if(item.badgeCount > 0) {
                                BadgeBox(
                                    badgeContent = {
                                        Text(text = item.badgeCount.toString())
                                    }
                                ) {
                                    Icon(
                                        imageVector = item.icon,
                                        contentDescription = item.name
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.name
                                )
                            }
                            if(selected) {
                                Text(
                                    text = item.name,
                                    textAlign = TextAlign.Center,
                                    fontSize = 10.sp
                                )
                            }
                        }
                    }
                )
            }
        }
    }
    @Composable
    fun MusicsScreen() {
        val adWidth = LocalConfiguration.current.screenWidthDp - 32
        val context = LocalContext.current
//        loadInterstitial(context)
//        showInterstitial(context)

        /*
        Column(modifier = Modifier
            .padding(10.dp)
            .fillMaxHeight()
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .horizontalScroll(rememberScrollState())
            ) {
            topic_head(head = "Poems By Swami Vivekananda")
                    Row(modifier = Modifier
                        .padding(10.dp)
                        .background(MaterialTheme.colors.primary)) {



                    }
            Spacer(modifier = Modifier.padding(top=15 .dp))
            topic_head(head = "Offerings to Swami Vivekananda")
            Row(modifier = Modifier
                .padding(10.dp)
                .fillMaxHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)) {

                 VideoPlayer()

            }


        }
         */

        Column {
            Spacer(modifier = Modifier.padding(top=15 .dp))
            topic_head(head = "About Us")
            Spacer(modifier = Modifier.padding(top=15 .dp))
            Text( modifier = Modifier.padding(all=15 .dp),
                text = " By the Grace of Bhagavan Sri Ramakrishna this App has been made . We appreciate all of you for bearing with our shortcomings and your support to us ."
            )
            //playAudio(context = LocalContext.current, int =R.raw.mediom, song_name = " Oo")
            Spacer(modifier = Modifier.padding(top=15 .dp))
            //topic_head(head = "Song Murta Maheswar : Violin")
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCmdfWmU0Jz16fVjXRw8EMRA"))
            val context = LocalContext.current
            Spacer(modifier = Modifier.padding(top=15 .dp))
            Button(onClick = {
                startActivity(intent, null) }
            ) {
                Text("Our Youtube Channel")
            }
            HomeScreen_1()
            AndroidView(
                factory = { context ->
                    AdView(context).apply {
                        this.setAdSize(AdSize.getCurrentOrientationInlineAdaptiveBannerAdSize(context,adWidth))
                        adUnitId = context.getString(R.string.ad_id_banner)
                        loadAd(AdRequest.Builder().build())
                    }
                }
            )


        }


   }
    @Composable
    fun bookcard2(book: Book) {
        Card( border = BorderStroke(1.dp, Color.Black), elevation = 8.dp,
            modifier=Modifier.padding(12 .dp),
            shape = MaterialTheme.shapes.small.copy(
                topStart= CornerSize(30 .dp),
                bottomStart = CornerSize(30 .dp), // overrides small theme style
                bottomEnd = ZeroCornerSize // overrides small theme style
            )) {

            var expanded by remember { mutableStateOf(false) }

            val extraPadding by animateDpAsState(
                if (expanded) 28.dp else 0.dp,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            )
            Column(modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(top = extraPadding.coerceAtLeast(0.dp))
                .background(MaterialTheme.colors.secondary)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))) {

                Row(
                    modifier = Modifier
                        .padding(all = 8.dp)

                ) {

                    Image(
                        //painter = painterResource(id = R.drawable.swamiji),
                        painter = painterResource(getid(book.author)),
                        contentDescription = "Swamiji",
                        modifier = Modifier
                            // Set image size to 40 dp
                            .size(80.dp)
                            // Clip image to be shaped as a circle
                            .clip(CircleShape)
                            .padding(top = 10.dp)

                    )
                    Column() {
                        Text(text = book.story, modifier = Modifier
                            .padding(10.dp)
                            .width(250.dp)
                            .padding(top = 10.dp),
                            fontSize = 14.sp,fontStyle = FontStyle.Normal, fontFamily = FontFamily.Serif,color = Color.Black)
                        Row(
                            modifier = Modifier

                                .padding(8.dp),
                            horizontalArrangement = Arrangement.Start
                        ) {
                            Image(
                                //painter = painterResource(id = R.drawable.swamiji),
                                painter = painterResource(R.drawable.flw_back),
                                contentDescription = "Flower",
                                modifier = Modifier
                                    // Set image size to 40 dp
                                    .size(40.dp)
                                    // Clip image to be shaped as a circle
                                    .clip(CircleShape)
                                    .padding(horizontal = 10.dp)
                                    .padding(bottom = 20.dp)

                            )
                            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                                Text(text = "Yours ${book.author} ",fontSize = 14.sp, fontFamily = FontFamily.Cursive,color = Color.Black)

                            }
                        }


                    }

                }



            }


        }
    }
    @Composable
    fun bookcard(book: Book) {
        Card( border = BorderStroke(1.dp, Color.Black), elevation = 8.dp,
            modifier=Modifier.padding(12 .dp),
            shape = MaterialTheme.shapes.small.copy(
                topStart= CornerSize(30 .dp),
                bottomStart = CornerSize(30 .dp), // overrides small theme style
                bottomEnd = ZeroCornerSize // overrides small theme style
            )) {

            var expanded by remember { mutableStateOf(false) }

            val extraPadding by animateDpAsState(
                if (expanded) 28.dp else 0.dp,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioHighBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            )
            Column(modifier = Modifier
                .clickable { expanded = !expanded }
                .padding(top = extraPadding.coerceAtLeast(0.dp))
                .background(MaterialTheme.colors.secondary)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))) {

                Row(
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .fillMaxWidth()
                ) {

                    Image(
                        //painter = painterResource(id = R.drawable.swamiji),
                        painter = painterResource(getid(book.author)),
                        contentDescription = "Swamiji",
                        modifier = Modifier
                            // Set image size to 40 dp
                            .size(140.dp)
                            // Clip image to be shaped as a circle
                            .clip(CircleShape)
                            .padding(top = 10.dp)

                    )

                    Text(text = book.story, modifier = Modifier
                        .padding(10.dp)
                        .padding(top = 10.dp),
                        fontSize = 15.sp,fontStyle = FontStyle.Normal, fontFamily = FontFamily.Serif, color = Color.Black)
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Image(
                        //painter = painterResource(id = R.drawable.swamiji),
                        painter = painterResource(R.drawable.flw_back),
                        contentDescription = "Swamiji",
                        modifier = Modifier
                            // Set image size to 40 dp
                            .size(40.dp)
                            // Clip image to be shaped as a circle
                            .clip(CircleShape)
                            .padding(horizontal = 10.dp)
                            .padding(bottom = 10.dp)

                    )
                    CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                        Text(text = "Yours ${book.author} ", fontFamily = FontFamily.Cursive,color = Color.Black)

                    }
                }


            }


        }
    }

    private fun getid(author: String): Int {
        when(author) {
            "Sw. Vivekananda" -> return R.drawable.swamiji
            "Sw Vivekananda" -> return R.drawable.swamiji
            "Sw Brahmananda" -> return R.drawable._1_swami_brahmananda_713x1024
            "Sw Premananda" -> return R.drawable._70x430_sw_premananda_mj
            "Sw Shivananda"-> return R.drawable._2_swami_shivananda_719x1024
            "Sw Abhedananda"-> return R.drawable._70x430_sw_abhedananda_mj
            "Sw Akhandananda" -> return R.drawable._3_swami_akhandananda_sqr2
            "Sw Adbhutananda"-> return R.drawable._70x430_sw_adbhutananda_mj
            "Sw Advaitananda"-> return R.drawable._70x430_sw_advaitananda_mj
            "Sw Niranjananda" -> return R.drawable._70x430_sw_niranjanananda_mj
            "Sw Ramakrishnananda"->  return R.drawable._70x430_sw_ramakrishnananda_mj
            "Sw Saradananda"-> return R.drawable._70x430_sw_saradananda_mj
            "Sw Trigunatitananda" ->  return R.drawable._70x430_sw_trigunatitananda_mj
            "Sw Turiyananda"-> return R.drawable._70x430_sw_turiyananda_mj
            "Sw Yogananda"-> return R.drawable._70x430_sw_yogananda_mj
            "Mother"-> return R.drawable.sri_sarada_devi_unknown
            "Ramakrishna"-> return R.drawable._200px_ramakrishna
            "Sw Vijnanananda"-> return R.drawable.swami_vijnanananda
            "Sw Subodhananda" -> return R.drawable.swami_subodhananda

            else ->{
                return R.drawable.monk
        }
        }
    }


    @ExperimentalAnimationApi

    @Composable
    fun Greeting(navController: NavHostController)


     {
        var visible by rememberSaveable { mutableStateOf(false)}
        var visible2 by rememberSaveable { mutableStateOf(false)}
        var visible3 by rememberSaveable { mutableStateOf(false)}
        var visible4 by rememberSaveable { mutableStateOf(false)}
        var visible5 by rememberSaveable { mutableStateOf(false)}
        // Add a horizontal space between the image and the column
        LaunchedEffect(key1 = true)
        {
            visible =true
            delay(1000)
            visible2=true
            delay(1000)
            visible3=true
            delay(1000)
            visible4=true
            delay(1000)
            visible5=true
        }

        val density = LocalDensity.current
        Spacer(modifier = Modifier.padding(vertical = 30 .dp))
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Row(modifier = Modifier.padding(all = 8.dp)) {
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(
                        initialAlpha = 0.1f,
                        animationSpec = tween(durationMillis = 2000)
                    )
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.swamiji),
                        contentDescription = "Swamiji",
                        modifier = Modifier
                            // Set image size to 40 dp
                            .size(160.dp)
                            // Clip image to be shaped as a circle
                            .clip(CircleShape)

                    )
                }


            }

            AnimatedVisibility(
                visible = visible2,
                /*
                enter = slideInHorizontally(
                    { fullWidth ->
                        // Offsets the content by 1/3 of its width to the left, and slide towards right
                        // Overwrites the default animation with tween for this slide animation.
                        fullWidth/3
                    }, animationSpec = tween(durationMillis =1000)
                )*/
            enter= fadeIn(initialAlpha = 0.1f, animationSpec = tween(durationMillis = 3000))
            ) {
            Surface(
                shape = MaterialTheme.shapes.small,
                elevation = 44.dp,
                //color = MaterialTheme.colors.background
            )
            {

                    Spacer(modifier = Modifier.padding(horizontal = 20.dp))
                    Text(
                        text = " My  Brothers and Sisters  ",
                        Modifier.padding(all = 8.dp),

                        )

                }
            }

            AnimatedVisibility(
                visible = visible3, enter =
                slideInHorizontally(
                    { fullWidth ->
                        // Offsets the content by 1/3 of its width to the left, and slide towards right
                        // Overwrites the default animation with tween for this slide animation.
                        fullWidth/2
                    }, animationSpec = tween(durationMillis = 3000)
                )
              // fadeIn(initialAlpha = 0.1f, animationSpec = tween(durationMillis = 3000))
            ) {


                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = " Emotion overtaking you ? ",
                    Modifier.padding(all = 8.dp),

                    )
            }


            AnimatedVisibility(
                visible = visible4, enter = slideInHorizontally(
                    { fullWidth ->
                        // Offsets the content by 1/3 of its width to the left, and slide towards right
                        // Overwrites the default animation with tween for this slide animation.
                        fullWidth / 3
                    }, animationSpec = tween(durationMillis = 3000)
                )
            ) {

                Text(text = "   Ask us .", Modifier.padding(all = 8.dp))
                Spacer(modifier = Modifier.padding(vertical = 20.dp))
                /*Surface(shape = MaterialTheme.shapes.large,elevation = 5.dp, color = MaterialTheme.colors.secondary){


                Text(text = "Popular Searches",
                    Modifier.padding(all = 8.dp),
                    color = Color.White

                )
            }

            var names = listOf("Angery", "Sad","Proud","Depressed","Sorrow","Cursed")
            Spacer(modifier = Modifier.padding(vertical = 10.dp))
              Row() {
                for (i in names)
                {   Search_text(text2 = i)
                }

            }*/
            }
           AnimatedVisibility(visible = visible5,enter = slideInHorizontally(
               { fullWidth ->
                   // Offsets the content by 1/3 of its width to the left, and slide towards right
                   // Overwrites the default animation with tween for this slide animation.
                   fullWidth / 3
               }, animationSpec = tween(durationMillis = 3000)
           )) {


            Row(horizontalArrangement = Arrangement.End, modifier = Modifier.fillMaxWidth()) {
                Button(
                    modifier = Modifier
                        .padding(vertical = 24.dp),

                    onClick = { navController.navigate("search") }
                ) {
                    Text("Continue")
                }
            }

             }
            Spacer(modifier = Modifier.height(10.dp))
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
                //CircularProgressIndicator()
            }
        }



        }
}







    @Composable
    fun Search_text(text2: String) {
        Surface(shape = MaterialTheme.shapes.small,)
        {

                Modifier.padding(all = 8.dp)
                Text(text = text2 ,Modifier.padding(all = 8.dp),
                    )




        }
    }


    @Composable
    fun DefaultPreview() {
        EmotionControlTheme {

        }
    }
