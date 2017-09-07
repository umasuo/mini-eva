'use strict'

var gulp = require('gulp')
var postcss = require('gulp-postcss')
var sass = require('gulp-sass')
var pug = require('gulp-pug')
var watch = require('gulp-watch')
var browserSync = require('browser-sync').create();

gulp.task('css', function () {
  return gulp.src('./src/scss/**/*.scss')
    .pipe(sass({
      includePaths: ['./src/scss/', './node_modules/bootstrap-sass/assets/stylesheets/']
    }).on('error', sass.logError))
    .pipe(postcss([
      require("postcss-cssnext")(),
      require("cssnano")({ autoprefixer: false }),
      require("postcss-browser-reporter")(),
      require("postcss-reporter")()
    ]))
    .pipe(gulp.dest('../../static/market-site/static/css'))
    .pipe(browserSync.stream({match: '**/*.css'}))
})

gulp.task('css:watch', ['css'], function () {})

gulp.task('views', function buildHTML() {
  return gulp.src('./src/*.pug')
  .pipe(pug({
    basedir: './src'
  }))
  .pipe(gulp.dest('../../static/market-site/'))
})

gulp.task('views:watch', ['views'], function () {
  browserSync.reload()
})

// copy files directly under src folder, and not with .pug extension
gulp.task('copy', function() {
  gulp.src(['./src/*.!(pug)', './src/static/**/*'], {base: './src'})
  .pipe(gulp.dest('../../static/market-site'))
})

gulp.task('watch', ['views', 'css', 'copy'], function () {
  browserSync.init({
    server: {
      baseDir: "../../static/market-site"
    }
  })

  watch('./src/static/**/*', function () {
    gulp.start('copy')
  })
  watch('./src/**/*.pug', function () {
    gulp.start('views:watch')
  })
  watch('./src/scss/**/*.scss', function () {
    gulp.start('css:watch')
  })
})

// developing (with live reload)
gulp.task('default', function() {
  gulp.start('watch')
})

// production
gulp.task('build', function() {
  gulp.start(['css', 'views', 'copy'])
})
