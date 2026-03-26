package ru.fedotov.myfirstapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.fedotov.myfirstapp.dto.Post
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PostRepositorySharedPrefsImpl(
    private val context: Context
) : PostRepository {

    private val gson = Gson()
    private val prefs = context.getSharedPreferences("posts_repo", Context.MODE_PRIVATE)
    private val type = object : TypeToken<List<Post>>() {}.type
    private val key = "posts"

    private var nextId = 1L
    private val currentUserId = 1L
    private val currentUserName = "Я"

    private var posts = emptyList<Post>()
    private val _data = MutableLiveData(posts)

    init {
        loadData()
    }

    override fun getAll(): LiveData<List<Post>> = _data

    override fun likeById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(
                    likedByMe = !post.likedByMe,
                    likes = if (post.likedByMe) post.likes - 1 else post.likes + 1
                )
            } else {
                post
            }
        }
        _data.value = posts
        saveData()
    }

    override fun shareById(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(shares = post.shares + 1)
            } else {
                post
            }
        }
        _data.value = posts
        saveData()
    }

    override fun increaseViews(id: Long) {
        posts = posts.map { post ->
            if (post.id == id) {
                post.copy(views = post.views + 1)
            } else {
                post
            }
        }
        _data.value = posts
        saveData()
    }

    override fun save(post: Post) {
        posts = if (post.id == 0L) {
            val newPost = post.copy(
                id = nextId++,
                author = currentUserName,
                authorId = currentUserId,
                published = formatDate(Date()),
                likedByMe = false,
                likes = 0,
                shares = 0,
                views = 0
            )
            listOf(newPost) + posts
        } else {
            posts.map { existingPost ->
                if (existingPost.id == post.id) {
                    existingPost.copy(content = post.content)
                } else {
                    existingPost
                }
            }
        }
        _data.value = posts
        saveData()
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        _data.value = posts
        saveData()
    }

    private fun loadData() {
        val json = prefs.getString(key, null)
        if (json != null) {
            try {
                val loadedPosts: List<Post> = gson.fromJson(json, type)
                if (loadedPosts.isNotEmpty()) {
                    posts = loadedPosts
                    nextId = (posts.maxOfOrNull { it.id } ?: 0) + 1
                    _data.value = posts
                    return
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        createInitialData()
        saveData()
    }

    private fun saveData() {
        prefs.edit().putString(key, gson.toJson(posts)).apply()
    }

    private fun createInitialData() {
        // Аналогично файловой реализации
        posts = listOf(
            Post(
                id = 1,
                author = "ШУБЫ!",
                content = "✨ Новая коллекция шуб уже в нашем магазине! ✨\n" +
                        "Представляем вам свежие модели, которые станут настоящим украшением вашего зимнего гардероба. В этом сезоне мы сделали ставку на:\n" +
                        "благородные оттенки (шоколад, графит, айвори);\n" +
                        "лаконичные силуэты;\n" +
                        "премиальное качество меха.\n" +
                        "Ждём вас в магазине",
                published = "21 мая в 18:36",
                likedByMe = false,
                likes = 999,
                views = 5700,
                video = "https://rutube.ru/video/f645cbc89bf04c129ac6089f8a725b91/"

            ),
            Post(
                id = 2,
                author = "ШУБЫ!",
                content = "✨ Идеальный зимний образ: новая коллекция уже здесь! ✨\n" +
                        "Мы рады представить вам поступление, которое изменит ваше представление об уюте. В этом сезоне мы соединили европейскую сдержанность и непревзойденное качество меха. В нашей новой коллекции вас ждут:\n" +
                        "— Благородная палитра: глубокий шоколад, холодный графит, нежный айвори и насыщенный черный;\n" +
                        "— Архитектура силуэта: летящие оверсайзы, женственные приталенные модели и лаконичные прямые крои;\n" +
                        "— Детали: натуральная кожа, итальянская фурнитура и утепленные манжеты.\n" +
                        "Не откладывайте на завтра тепло, которое выглядит как искусство. Ждём вас в магазине! ❄",
                published = "22 мая в 10:15",
                likedByMe = false,
                likes = 342,
                shares = 89,
                views = 2300,
                video = "https://rutube.ru/video/c1184d00bf4872d31c369c24476ca4c0/"


            ),
            Post(
                id = 3,
                author = "ШУБЫ!",
                content = "Холода уже близко? Встречаем их достойно!" +
                        "Забудьте о том, что шуба — это просто одежда. Это ваша личная зона тепла, комфорта и безупречного стиля. В нашем новом завозе мы собрали модели, которые дарят ощущение уюта с первой секунды.\n" +
                        "Почему стоит приехать к нам уже сегодня?",
                published = "23 мая в 09:42",
                likedByMe = true,
                likes = 1250,
                shares = 420,
                views = 8900,
                video = "https://rutube.ru/video/c1ef69af32bb653d5221f1b75bf2d987/"  // пример видео

            ),
            Post(
                id = 4,
                author = "ШУБЫ!",
                content = "ШУБЫ, ОТ КОТОРЫХ ЗАХВАТЫВАЕТ ДУХ (в прямом смысле, потому что на улице ветер)!" +
                        "Друзья, у нас пополнение! И оно настолько красивое, что мы решили не спать ночь, чтобы распаковать все коробки. Ловите три главных настроения новой коллекции:\n" +
                        " ВАЙБ «ДОРОГО-БОГАТО»: Шоколадные оттенки в сочетании с длинным ворсом. Для тех, кто хочет чувствовать себя кинозвездой даже в очереди за кофе.\n" +
                        " ВАЙБ «НЕВЕСТА»: Айвори, мягкий объем и женственные линии. Идеально для романтичных натур.\n" +
                        " ВАЙБ «СМЕЛОСТЬ»: Графит и короткие модели-авиаторы. Для тех, кто не боится экспериментов и ценит динамику.\n" +
                        "Приходите выбирать свой характер. Ждём вас в магазине! ❤",
                published = "20 мая в 20:00",
                likedByMe = false,
                likes = 5678,
                shares = 1234,
                views = 45000,
                video = "https://rutube.ru/video/4aa7de37f1b240c6d541ce1fc63a8fa2/"  // пример видео

            ),
            Post(
                id = 5,
                author = "ШУБЫ!",
                content = "❄ Секрет идеальной шубы: почему новая коллекция — это инвестиция в себя. ❄" +
                        "Мы часто слышим вопрос: «Почему стоит покупать шубу именно сейчас?». Отвечаем! Новая партия, поступившая в наш магазин, — это уникальное сочетание цены и качества, доступное только в период начала сезона.\n" +
                        "В этой коллекции мы сделали акцент на практичность:\n" +
                        "✅ Облегченные модели: Шубы стали легче за счет высокотехнологичной выделки, но стали теплее.\n" +
                        "✅ Устойчивость к влаге: Специальная пропитка меха (для мутонов и норки) позволяет изделиям сохранять вид даже в мокрый снег.\n" +
                        "✅ Универсальность: Модели-трансформеры со съемными капюшонами и регулируемыми поясами.\n" +
                        "Выбирайте лучшее для себя. Ждём вас в магазине, чтобы показать все преимущества вживую!",
                published = "24 июня в 17:30",
                likedByMe = false,
                likes = 3668,
                shares = 1234,
                views = 13992,
                video = "https://rutube.ru/video/064f2834ab552697af4350f1d9dd5511/"

            ),
            Post(
                id = 6,
                author = "ШУБЫ!",
                content = "✨ Примерь роскошь: твой выход в свет начинается с порога нашего магазина. ✨\n" +
                        "Знаете это чувство, когда надеваешь качественную вещь, и весь мир начинает улыбаться в ответ? Именно такие эмоции мы вложили в нашу новую коллекцию шуб.\n" +
                        "В этом сезоне мы нарушили правила и привезли модели, которые сложно найти у конкурентов:\n" +
                        "— Эффект «второй кожи» в моделях из скандинавской норки;\n" +
                        "— Графитовый блеск, который визуально вытягивает силуэт и делает образ дорогим;\n" +
                        "— Лаконичные линии, которые не перегружают образ, оставляя главной героиней вас.\n" +
                        "Не ждите особого повода, чтобы выглядеть великолепно. Повод уже есть — обновление витрины! Ждём вас в магазине. \uD83E\uDD42",
                published = "24 июня в 15:15",
                likedByMe = false,
                likes = 5348,
                shares = 234,
                views = 5321,
                video = "https://rutube.ru/video/5dcb100638ad62f19f099d76cec8b769/"

            ),
            Post(
                id = 7,
                author = "ШУБЫ!",
                content = " НОВИНКА: ВИДЕТЬ И ПЛАКАТЬ (от счастья)! " +
                        "Девчонки, это любовь с первого взгляда. В наш магазин приехали шубы, которые хочется гладить часами. Мех настолько мягкий, что напоминает облако. ☁\uFE0F\n" +
                        "Что особенного в этом завозе?\n" +
                        "• Цвет: мы добавили модный графит и шоколад. Они выглядят очень дорого и не маркие (важный пункт для зимы!).\n" +
                        "• Крой: лаконичные, с четкими плечами. Никаких бабушкиных «колоколов», только современная классика.\n" +
                        "• Качество: итальянская подкладка, которая не электризует волосы, и надежные крючки.\n" +
                        "Скорее приезжайте, пока эти красавицы не разобрали наши постоянные клиентки! Ждём вас в магазине. ❤",
                published = "20 мая в 20:00",
                likedByMe = false,
                likes = 8072,
                views = 21462,
                shares = 1142,
                video = "https://rutube.ru/video/96a896bfacd1811a128e2a5118d5782a/"  // пример видео

            ),
            Post(
                id = 8,
                author = "ШУБЫ!",
                content = " Мужчинам на заметку: лучший подарок для любимой женщины — это тепло ее мечты. " +
                        "Выбираете презент на годовщину или просто хотите порадовать свою королеву? Забудьте о банальных букетах. В нашем магазине стартовал сезон продаж новой коллекции шуб, и это именно то, что вызовет искренние слезы радости.\n" +
                        "Почему стоит выбрать нас:\n" +
                        " Мы поможем с выбором: подскажем размер, подберем фасон под тип фигуры (приталенные скроют лишнее, оверсайз добавят шарма).\n" +
                        " Благородные оттенки: шоколад, графит, айвори — универсальные цвета, которые подойдут к любому гардеробу.\n" +
                        " Премиальное качество: мех, который прослужит 10+ лет.\n" +
                        "Ждём вас в магазине. Позвольте себе сделать самый желанный подарок!",
                published = "5 марта в 20:00",
                likedByMe = false,
                likes = 366,
                shares = 33,
                views = 333333,
                video = "https://rutube.ru/video/e77a56d4d8e6d7753abc381f854898b7/"  // пример видео

            ),
            Post(
                id = 9,
                author = "ШУБЫ!",
                content = "Почему шубы из новой коллекции лучше тех, что вы видели раньше?\n" +
                        "Мы постоянно совершенствуем ассортимент, и этот сезон — не исключение. В новой партии мы учли пожелания наших клиентов и добавили модели, которые решают главные зимние проблемы.\n" +
                        "1. Больше благородства. Вместо кричащих кислотных цветов — элегантный шоколад, сдержанный графит и нежный айвори. Эти цвета не выходят из моды и выглядят дорого.\n" +
                        "2. Свобода движений. Лаконичные силуэты не сковывают движения. Вы можете комфортно управлять автомобилем или носить ребенка на руках.\n" +
                        "3. Экологичность и этика. Мы работаем с сертифицированными производителями, гарантируя долговечность изделий.\n" +
                        "Приходите сравнить качество сами. Ждём вас в магазине!",
                published = "20 мая в 20:00",
                likedByMe = false,
                likes = 333,
                shares = 444,
                views = 8743,
                video = "https://rutube.ru/video/3ecdf50b07cd762cfda9a0eb228e2918/"  // пример видео

            ),
            Post(
                id = 10,
                author = "ШУБЫ!",
                content = " В воздухе пахнет первым снегом и... новой шубой!\n" +
                        "Мы обожаем этот момент, когда природа готовится к зимней сказке, а в нашем магазине распаковывают самую красивую коллекцию сезона. Сегодня мы хотим пригласить вас в мир, где холод — не повод прятаться, а повод блистать.\n" +
                        "Представьте: мягкий мех благородного оттенка шоколада или графита идеально лежит на плечах. Лаконичный силуэт подчеркивает достоинства фигуры, а внутренняя отделка радует глаз. В этой шубе вы будете не просто греть — вы будете создавать настроение вокруг себя.\n" +
                        "Приходите выбирать свою зимнюю историю. Ждём вас в магазине, чтобы вместе найти «ту самую» модель!",
                published = "20 мая в 20:00",
                likedByMe = false,
                likes = 373,
                shares = 2413,
                views = 64635,
                video = "https://rutube.ru/video/0872c244237b298d3d8f263a0c3cf282/"  // пример видео

            )

        )
        _data.value = posts
    }

    private fun formatDate(date: Date): String {
        val format = SimpleDateFormat("d MMM в HH:mm", Locale("ru"))
        return format.format(date)
    }
}
