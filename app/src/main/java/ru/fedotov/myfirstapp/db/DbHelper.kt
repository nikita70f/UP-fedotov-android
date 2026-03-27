package ru.fedotov.myfirstapp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import ru.fedotov.myfirstapp.db.PostContract.Columns

class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "myfirstapp.db"
        private const val DATABASE_VERSION = 1

        // SQL для создания таблицы
        private const val SQL_CREATE_POSTS =
            "CREATE TABLE ${PostContract.TABLE_NAME} (" +
                    "${Columns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "${Columns.AUTHOR} TEXT NOT NULL," +
                    "${Columns.AUTHOR_ID} INTEGER NOT NULL," +
                    "${Columns.CONTENT} TEXT NOT NULL," +
                    "${Columns.PUBLISHED} TEXT NOT NULL," +
                    "${Columns.LIKED_BY_ME} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.LIKES} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.SHARES} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.VIEWS} INTEGER NOT NULL DEFAULT 0," +
                    "${Columns.VIDEO} TEXT" +
                    ")"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Создаем таблицу при первом запуске
        db.execSQL(SQL_CREATE_POSTS)

        // Здесь можно добавить начальные данные
        insertInitialData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // При обновлении версии удаляем старую таблицу и создаем новую
        // В реальном проекте здесь должна быть миграция данных
        db.execSQL("DROP TABLE IF EXISTS ${PostContract.TABLE_NAME}")
        onCreate(db)
    }

    private fun insertInitialData(db: SQLiteDatabase) {
        // Вставляем начальные посты для демонстрации
        val contentValues = android.content.ContentValues().apply {
            put(Columns.AUTHOR, "ШУБЫ!")
            put(Columns.AUTHOR_ID, 2)
            put(Columns.CONTENT, "✨ Новая коллекция шуб уже в нашем магазине! ✨\n" +
                    "Представляем вам свежие модели, которые станут настоящим украшением вашего зимнего гардероба. В этом сезоне мы сделали ставку на:\n" +
                    "благородные оттенки (шоколад, графит, айвори);\n" +
                    "лаконичные силуэты;\n" +
                    "премиальное качество меха.\n" +
                    "Ждём вас в магазине"
            )
            put(Columns.PUBLISHED, "21 мая в 18:36")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 999)
            put(Columns.SHARES, 25)
            put(Columns.VIEWS, 5700)
            put(Columns.VIDEO, "https://rutube.ru/video/f645cbc89bf04c129ac6089f8a725b91/")
        }
        db.insert(PostContract.TABLE_NAME, null, contentValues)


// Post 2
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "ШУБЫ!")
            put(Columns.AUTHOR_ID, 3)
            put(Columns.CONTENT, "✨ Идеальный зимний образ: новая коллекция уже здесь! ✨\n" +
                    "Мы рады представить вам поступление, которое изменит ваше представление об уюте. В этом сезоне мы соединили европейскую сдержанность и непревзойденное качество меха. В нашей новой коллекции вас ждут:\n" +
                    "— Благородная палитра: глубокий шоколад, холодный графит, нежный айвори и насыщенный черный;\n" +
                    "— Архитектура силуэта: летящие оверсайзы, женственные приталенные модели и лаконичные прямые крои;\n" +
                    "— Детали: натуральная кожа, итальянская фурнитура и утепленные манжеты.\n" +
                    "Не откладывайте на завтра тепло, которое выглядит как искусство. Ждём вас в магазине! ❄")
            put(Columns.PUBLISHED, "22 мая в 10:15")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 342)
            put(Columns.SHARES, 89)
            put(Columns.VIEWS, 2300)
            put(Columns.VIDEO, "https://rutube.ru/video/c1184d00bf4872d31c369c24476ca4c0/")
            db.insert(PostContract.TABLE_NAME, null, this)
        }

// Post 3
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "ШУБЫ!")
            put(Columns.AUTHOR_ID, 3)
            put(Columns.CONTENT, "Холода уже близко? Встречаем их достойно!\n" +
                    "Забудьте о том, что шуба — это просто одежда. Это ваша личная зона тепла, комфорта и безупречного стиля. В нашем новом завозе мы собрали модели, которые дарят ощущение уюта с первой секунды.\n" +
                    "Почему стоит приехать к нам уже сегодня?")
            put(Columns.PUBLISHED, "23 мая в 09:42")
            put(Columns.LIKED_BY_ME, 1)
            put(Columns.LIKES, 1250)
            put(Columns.SHARES, 420)
            put(Columns.VIEWS, 8900)
            put(Columns.VIDEO, "https://rutube.ru/video/c1ef69af32bb653d5221f1b75bf2d987/")
            db.insert(PostContract.TABLE_NAME, null, this)
        }

// Post 4
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "ШУБЫ!")
            put(Columns.AUTHOR_ID, 3)
            put(Columns.CONTENT, "ШУБЫ, ОТ КОТОРЫХ ЗАХВАТЫВАЕТ ДУХ (в прямом смысле, потому что на улице ветер)!\n" +
                    "Друзья, у нас пополнение! И оно настолько красивое, что мы решили не спать ночь, чтобы распаковать все коробки. Ловите три главных настроения новой коллекции:\n" +
                    " ВАЙБ «ДОРОГО-БОГАТО»: Шоколадные оттенки в сочетании с длинным ворсом. Для тех, кто хочет чувствовать себя кинозвездой даже в очереди за кофе.\n" +
                    " ВАЙБ «НЕВЕСТА»: Айвори, мягкий объем и женственные линии. Идеально для романтичных натур.\n" +
                    " ВАЙБ «СМЕЛОСТЬ»: Графит и короткие модели-авиаторы. Для тех, кто не боится экспериментов и ценит динамику.\n" +
                    "Приходите выбирать свой характер. Ждём вас в магазине! ❤")
            put(Columns.PUBLISHED, "20 мая в 20:00")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 5678)
            put(Columns.SHARES, 1234)
            put(Columns.VIEWS, 45000)
            put(Columns.VIDEO, "https://rutube.ru/video/4aa7de37f1b240c6d541ce1fc63a8fa2/")
            db.insert(PostContract.TABLE_NAME, null, this)
        }

// Post 5
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "ШУБЫ!")
            put(Columns.AUTHOR_ID, 3)
            put(Columns.CONTENT, "❄ Секрет идеальной шубы: почему новая коллекция — это инвестиция в себя. ❄\n" +
                    "Мы часто слышим вопрос: «Почему стоит покупать шубу именно сейчас?». Отвечаем! Новая партия, поступившая в наш магазин, — это уникальное сочетание цены и качества, доступное только в период начала сезона.\n" +
                    "В этой коллекции мы сделали акцент на практичность:\n" +
                    "✅ Облегченные модели: Шубы стали легче за счет высокотехнологичной выделки, но стали теплее.\n" +
                    "✅ Устойчивость к влаге: Специальная пропитка меха (для мутонов и норки) позволяет изделиям сохранять вид даже в мокрый снег.\n" +
                    "✅ Универсальность: Модели-трансформеры со съемными капюшонами и регулируемыми поясами.\n" +
                    "Выбирайте лучшее для себя. Ждём вас в магазине, чтобы показать все преимущества вживую!")
            put(Columns.PUBLISHED, "24 июня в 17:30")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 3668)
            put(Columns.SHARES, 1234)
            put(Columns.VIEWS, 13992)
            put(Columns.VIDEO, "https://rutube.ru/video/064f2834ab552697af4350f1d9dd5511/")
            db.insert(PostContract.TABLE_NAME, null, this)
        }

// Post 6
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "ШУБЫ!")
            put(Columns.AUTHOR_ID, 3)
            put(Columns.CONTENT, "✨ Примерь роскошь: твой выход в свет начинается с порога нашего магазина. ✨\n" +
                    "Знаете это чувство, когда надеваешь качественную вещь, и весь мир начинает улыбаться в ответ? Именно такие эмоции мы вложили в нашу новую коллекцию шуб.\n" +
                    "В этом сезоне мы нарушили правила и привезли модели, которые сложно найти у конкурентов:\n" +
                    "— Эффект «второй кожи» в моделях из скандинавской норки;\n" +
                    "— Графитовый блеск, который визуально вытягивает силуэт и делает образ дорогим;\n" +
                    "— Лаконичные линии, которые не перегружают образ, оставляя главной героиней вас.\n" +
                    "Не ждите особого повода, чтобы выглядеть великолепно. Повод уже есть — обновление витрины! Ждём вас в магазине. 🥂")
            put(Columns.PUBLISHED, "24 июня в 15:15")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 5348)
            put(Columns.SHARES, 234)
            put(Columns.VIEWS, 5321)
            put(Columns.VIDEO, "https://rutube.ru/video/5dcb100638ad62f19f099d76cec8b769/")
            db.insert(PostContract.TABLE_NAME, null, this)
        }

// Post 7
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "ШУБЫ!")
            put(Columns.AUTHOR_ID, 3)
            put(Columns.CONTENT, "НОВИНКА: ВИДЕТЬ И ПЛАКАТЬ (от счастья)!\n" +
                    "Девчонки, это любовь с первого взгляда. В наш магазин приехали шубы, которые хочется гладить часами. Мех настолько мягкий, что напоминает облако. ☁️\n" +
                    "Что особенного в этом завозе?\n" +
                    "• Цвет: мы добавили модный графит и шоколад. Они выглядят очень дорого и не маркие (важный пункт для зимы!).\n" +
                    "• Крой: лаконичные, с четкими плечами. Никаких бабушкиных «колоколов», только современная классика.\n" +
                    "• Качество: итальянская подкладка, которая не электризует волосы, и надежные крючки.\n" +
                    "Скорее приезжайте, пока эти красавицы не разобрали наши постоянные клиентки! Ждём вас в магазине. ❤")
            put(Columns.PUBLISHED, "20 мая в 20:00")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 8072)
            put(Columns.SHARES, 1142)
            put(Columns.VIEWS, 21462)
            put(Columns.VIDEO, "https://rutube.ru/video/96a896bfacd1811a128e2a5118d5782a/")
            db.insert(PostContract.TABLE_NAME, null, this)
        }

// Post 8
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "ШУБЫ!")
            put(Columns.AUTHOR_ID, 3)
            put(Columns.CONTENT, "Мужчинам на заметку: лучший подарок для любимой женщины — это тепло ее мечты.\n" +
                    "Выбираете презент на годовщину или просто хотите порадовать свою королеву? Забудьте о банальных букетах. В нашем магазине стартовал сезон продаж новой коллекции шуб, и это именно то, что вызовет искренние слезы радости.\n" +
                    "Почему стоит выбрать нас:\n" +
                    " Мы поможем с выбором: подскажем размер, подберем фасон под тип фигуры (приталенные скроют лишнее, оверсайз добавят шарма).\n" +
                    " Благородные оттенки: шоколад, графит, айвори — универсальные цвета, которые подойдут к любому гардеробу.\n" +
                    " Премиальное качество: мех, который прослужит 10+ лет.\n" +
                    "Ждём вас в магазине. Позвольте себе сделать самый желанный подарок!")
            put(Columns.PUBLISHED, "5 марта в 20:00")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 366)
            put(Columns.SHARES, 33)
            put(Columns.VIEWS, 333333)
            put(Columns.VIDEO, "https://rutube.ru/video/e77a56d4d8e6d7753abc381f854898b7/")
            db.insert(PostContract.TABLE_NAME, null, this)
        }

// Post 9
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "ШУБЫ!")
            put(Columns.AUTHOR_ID, 3)
            put(Columns.CONTENT, "Почему шубы из новой коллекции лучше тех, что вы видели раньше?\n" +
                    "Мы постоянно совершенствуем ассортимент, и этот сезон — не исключение. В новой партии мы учли пожелания наших клиентов и добавили модели, которые решают главные зимние проблемы.\n" +
                    "1. Больше благородства. Вместо кричащих кислотных цветов — элегантный шоколад, сдержанный графит и нежный айвори. Эти цвета не выходят из моды и выглядят дорого.\n" +
                    "2. Свобода движений. Лаконичные силуэты не сковывают движения. Вы можете комфортно управлять автомобилем или носить ребенка на руках.\n" +
                    "3. Экологичность и этика. Мы работаем с сертифицированными производителями, гарантируя долговечность изделий.\n" +
                    "Приходите сравнить качество сами. Ждём вас в магазине!")
            put(Columns.PUBLISHED, "20 мая в 20:00")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 333)
            put(Columns.SHARES, 444)
            put(Columns.VIEWS, 8743)
            put(Columns.VIDEO, "https://rutube.ru/video/3ecdf50b07cd762cfda9a0eb228e2918/")
            db.insert(PostContract.TABLE_NAME, null, this)
        }

// Post 10
        android.content.ContentValues().apply {
            put(Columns.AUTHOR, "ШУБЫ!")
            put(Columns.AUTHOR_ID, 3)
            put(Columns.CONTENT, "В воздухе пахнет первым снегом и... новой шубой!\n" +
                    "Мы обожаем этот момент, когда природа готовится к зимней сказке, а в нашем магазине распаковывают самую красивую коллекцию сезона. Сегодня мы хотим пригласить вас в мир, где холод — не повод прятаться, а повод блистать.\n" +
                    "Представьте: мягкий мех благородного оттенка шоколада или графита идеально лежит на плечах. Лаконичный силуэт подчеркивает достоинства фигуры, а внутренняя отделка радует глаз. В этой шубе вы будете не просто греть — вы будете создавать настроение вокруг себя.\n" +
                    "Приходите выбирать свою зимнюю историю. Ждём вас в магазине, чтобы вместе найти «ту самую» модель!")
            put(Columns.PUBLISHED, "20 мая в 20:00")
            put(Columns.LIKED_BY_ME, 0)
            put(Columns.LIKES, 373)
            put(Columns.SHARES, 2413)
            put(Columns.VIEWS, 64635)
            put(Columns.VIDEO, "https://rutube.ru/video/0872c244237b298d3d8f263a0c3cf282/")
            db.insert(PostContract.TABLE_NAME, null, this)
        }
    }
}
