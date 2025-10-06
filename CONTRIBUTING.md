# 🤝 المساهمة في CloudStream Extensions Arabic

نرحب بالمساهمات في هذا المشروع! دليل المساهمة هذا سيساعدك على فهم كيفية المشاركة بفعالية.

## 📋 فهرس المحتويات

- [🤝 المساهمة في CloudStream Extensions Arabic](#-المساهمة-في-cloudstream-extensions-arabic)
  - [📋 فهرس المحتويات](#-فهرس-المحتويات)
  - [🌟 لماذا تساهم؟](#-لماذا-تساهم)
  - [🚀 كيف تبدأ](#-كيف-تبدأ)
  - [📝 أنواع المساهمات](#-أنواع-المساهمات)
  - [🔧 معايير الكود](#-معايير-الكود)
  - [🧪 اختبار مساهمتك](#-اختبار-مساهمتك)
  - [📤 إرسال المساهمة](#-إرسال-المساهمة)
  - [🎯 أولويات المشروع](#-أولويات-المشروع)
  - [📞 الحصول على المساعدة](#-الحصول-على-المساعدة)

## 🌟 لماذا تساهم؟

- 🎯 **تحسين تجربة المستخدم العربي**
- 🚀 **تعلم وتطوير مهاراتك البرمجية**
- 🤝 **الانضمام إلى مجتمع مبرمجين**
- 🏆 **الحصول على اعتراف بمساهماتك**
- 📈 **بناء سيرة ذاتية قوية**

## 🚀 كيف تبدأ

### 1. 🍴 Fork المستودع
```bash
# انسخ المستودع إلى حسابك
https://github.com/dhomred/cloudstream-extensions-arabic-v2/fork
```

### 2. 📥 Clone المستودع المحلي
```bash
git clone https://github.com/YOUR_USERNAME/cloudstream-extensions-arabic-v2.git
cd cloudstream-extensions-arabic-v2
```

### 3. 🌿 إنشاء فرع جديد
```bash
git checkout -b feature/اسم-الميزة-الجديدة
# أو
git checkout -b fix/اسم-الإصلاح
```

### 4. 🔧 تثبيت المتطلبات
```bash
# تأكد من وجود Java 11+
java -version

# تأكد من وجود Gradle
gradle --version

# تأكد من وجود Python 3.8+
python --version
```

## 📝 أنواع المساهمات

### ✅ إضافة مزود جديد
```kotlin
// مثال: مزود جديد
class NewProvider : MainAPI() {
    override var mainUrl = "https://example.com"
    override var name = "مزود جديد"
    override val hasMainPage = true
    override var lang = "ar"
    
    // تنفيذ وظائف المزود
}
```

### 🔧 إصلاح الأخطاء
- 🐛 **أخطاء البرمجة**: تصحيح الأخطاء المنطقية
- ⚡ **أخطاء الأداء**: تحسين سرعة التنفيذ
- 🛡️ **أخطاء الأمان**: إصلاح نقاط الضعف
- 📱 **أخطاء التوافق**: دعم الأجهزة المختلفة

### 📚 تحسين التوثيق
- 📝 **تحديث README.md**
- 📖 **إضافة أمثلة جديدة**
- 🔍 **تحسين التعليقات البرمجية**
- 🌍 **ترجمة التوثيق**

### 🧪 إضافة اختبارات
```kotlin
// مثال: اختبار جديد
@Test
fun testProviderFunctionality() {
    val provider = NewProvider()
    assertNotNull(provider.mainUrl)
    assertEquals("ar", provider.lang)
}
```

### 🎨 تحسين الواجهة
- 🖼️ **تحسين التصميم**
- 🎯 **تحسين تجربة المستخدم**
- 📱 **تحسين التوافق مع الأجهزة المحمولة**
- 🌈 **تحسين الألوان والخطوط**

## 🔧 معايير الكود

### 📋 تنسيق الكود
```kotlin
// ✅ جيد
class ProviderName : MainAPI() {
    override var mainUrl = "https://example.com"
    override var name = "اسم المزود"
    
    override suspend fun load(url: String): LoadResponse? {
        return try {
            // تنفيذ الوظيفة
        } catch (e: Exception) {
            null
        }
    }
}

// ❌ سيئ
class providername:MainAPI(){
override var mainurl="https://example.com"
override var name="اسم المزود"
}
```

### 🏷️ التسمية
- **الفئات**: `PascalCase` (مثال: `ProviderName`)
- **الدوال والمتغيرات**: `camelCase` (مثال: `getData()`)
- **الثوابت**: `UPPER_SNAKE_CASE` (مثال: `MAX_RETRIES`)
- **الملفات**: `kebab-case` (مثال: `new-provider.kt`)

### 💬 التعليقات
```kotlin
/**
 * تحميل البيانات من المزود
 * @param url رابط الصفحة
 * @return بيانات التحميل أو null في حال الفشل
 */
suspend fun load(url: String): LoadResponse? {
    // محاولة تحميل البيانات
    return try {
        // ... الكود
    } catch (e: Exception) {
        // تسجيل الخطأ
        logError(e)
        null
    }
}
```

### 🛡️ معايير الأمان
- ✅ **التحقق من صحة المدخلات**
- ✅ **معالجة الاستثناءات بشكل صحيح**
- ✅ **عدم تسجيل المعلومات الحساسة**
- ✅ **استخدام HTTPS دائماً**

## 🧪 اختبار مساهمتك

### 🔍 اختبار محلي
```bash
# اختبار البناء
./gradlew build

# اختبار الوظائف
./gradlew test

# تحليل الكود
python scripts/analyze.py

# اختبار المزود الجديد
python scripts/test_provider.py --provider NewProvider
```

### 📊 فحص الجودة
```bash
# فحص الأخطاء
./gradlew lint

# تنسيق الكود
./gradlew ktlintFormat

# تحليل الأمان
./gradlew dependencyCheckAnalyze
```

### 🧪 اختبار الوظائف
```kotlin
// مثال: اختبار شامل
@Test
fun testProviderComprehensive() {
    val provider = NewProvider()
    
    // اختبار الرابط
    assertTrue(provider.mainUrl.startsWith("https://"))
    
    // اختبار اللغة
    assertEquals("ar", provider.lang)
    
    // اختبار التحميل
    val result = runBlocking {
        provider.load("https://example.com/movie/123")
    }
    assertNotNull(result)
}
```

## 📤 إرسال المساهمة

### 1. ✅ اختبار التغييرات
```bash
# تأكد من أن جميع الاختبارات تمر
./gradlew test

# تأكد من أن البناء ينجح
./gradlew build

# تأكد من عدم وجود أخطاء
./gradlew lint
```

### 2. 📝 كتابة رسالة ارتكاب جيدة
```bash
# رسالة ارتكاب جيدة
git commit -m "إضافة مزود جديد: ProviderName

- دعم أفلام ومسلسلات عربية
- استخراج جودات متعددة
- معالجة الأخطاء الشاملة

Fixes #123"
```

### 3. 📤 دفع التغييرات
```bash
git push origin feature/اسم-الميزة-الجديدة
```

### 4. 📋 فتح Pull Request
- 🏷️ **عنوان واضح**: "إضافة مزود جديد: ProviderName"
- 📝 **وصف شامل**: اشرح ما الذي تم تغييره ولماذا
- ✅ **قائمة التحقق**: تأكد من إكمال جميع المتطلبات
- 🔗 **ربط القضايا**: أضف "Fixes #123" إذا كان يرتبط بقضية

## 🎯 أولويات المشروع

### 🔥 أولوية عالية
- 🆕 **إضافة مزودين جدد** (خصوصاً المواقع العربية)
- 🔧 **إصلاح الأخطاء الحرجة**
- 🛡️ **تحسين الأمان**
- 📱 **تحسين التوافق مع الأجهزة المحمولة**

### ⚡ أولوية متوسطة
- 🚀 **تحسين الأداء**
- 📚 **تحسين التوثيق**
- 🧪 **إضافة اختبارات جديدة**
- 🎨 **تحسين الواجهة**

### 💡 أولوية منخفضة
- 🌍 **الترجمة إلى لغات أخرى**
- 🎯 **إضافة ميزات إضافية**
- 📊 **تحسين التحليلات**
- 🔍 **تحسين محركات البحث**

## 📞 الحصول على المساعدة

### 💬 قنوات التواصل
- **GitHub Issues**: للإبلاغ عن الأخطاء
- **GitHub Discussions**: لمناقشة الميزات الجديدة
- **Pull Request Comments**: لمراجعة الكود

### 📚 الموارد
- **التوثيق الرسمي**: [docs/README.md](docs/README.md)
- **أمثلة الكود**: انظر إلى المزودين الحاليين
- **اختبارات المرجعية**: [tests/](tests/)

### 🆘 المساعدة الفنية
إذا واجهت مشكلة تقنية:

1. 🔍 **ابحث في القضايا الحالية** عن حل
2. 📖 **اقرأ التوثيق** بعناية
3. 💬 **افتح قضية جديدة** مع تفاصيل المشكلة
4. 🎯 **كن محدداً** في وصف المشكلة

---

**شكراً لمساهمتك في تحسين تجربة المستخدم العربي! 🙏**

<p align="center">
  ⭐ <strong>إذا أعجبك المشروع، لا تنسى أن تمنحنا نجمة!</strong> ⭐
</p>