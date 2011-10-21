;; Ororo is a library for working with the wunderground API. The API is pretty simple and
;; not too large, so it can mostly be done automatically. Some pieces are a bit inconsistent
;; and are implemented differently.
(ns ororo.core
  (:use [clojure.data.json :only [read-json pprint-json]]
        [clojure.string :only [join]])
  (:require [clj-http.client :as http]))

(def base-url "http://api.wunderground.com/api/")

(defn- create-url
  "Intelligently create a URL out of an API key, features (api calls), and
   the query location."
  [key features location]
  (str base-url key "/"
       (if (coll? features)
         (join "/" (map name features))
         (name features))
       "/q/"
       (.replace
        (if (string? location)
          location
          (join "/" (reverse location)))
        " "
        "_")
       ".json"))

(defn- sift [m f]
  (if f
    (or (f m) (-> m :response :results))
    m))

;; Ororo provides a lot of functions for working with the wunderground API by default.
;; However, wunderground allows you to get more than one type of data in a single request.
;; This cuts down on API requests one has to make to get a lot of information. For those
;; situations, you can use this function to execute a request yourself.
(defn api-call
  "Make an API call out of a key, some features (as keywords, or just one keyword),
   a location, and a 'sifting' function that will be applied to the resulting map."
  [key features location sift-fn]
  (-> (create-url key features location)
      http/get
      :body
      read-json
      (sift sift-fn)))

(defn- api-fn
  "Create a function that makes an API call based on a bit of info."
  [feature sift-fn]
  (fn [key location]
    (api-call key feature location sift-fn)))

;; We're not going to generate these functions automatically, since that'd require
;; making things unnecessarily complex with macro. We wouldn't gain much either way.
;; Instead, we'll define each of our API functions separately using a convenient
;; `api-fn` function and def. This is easier since we're on 1.3 and have inline
;; docstrings in `def`.
(def geolookup
  "Returns the city name, zip code/postal code, latitude-longitude coordinates and
   and nearby weather stations."
  (api-fn :geolookup :location))

(def conditions
  "Returns the current temperature, weather condition, humidity, wind, 'feels like'
   temperature, barometric pressure, and visibility."
  (api-fn :conditions :current_observation))

(def forecast
  "Returns a summar of the weather for the next 3 days. This includes high and low
   temperatures, a string text forecast, and the conditions."
  (api-fn :forecast :forecast))

(def astronomy "Returns the moon phase, sunrise, and sunset times."
  (api-fn :astronomy :moon_phase))

(def radar "Returns links to radar images."
  (api-fn :radar :radar))

(def satellite "Returns links to visual and infrared satellite images."
  (api-fn :satellite :satellite))

(def webcams
  "Returns locations of nearby Personal Weather Stations and URLS for
   images from their web cams."
  (api-fn :webcams :webcams))

(def alerts
  "Returns the short name description, expiration time, and a long text description 
   of a severe weather alert if one has been issued for the searched."
  (api-fn :alerts :alerts))

(def hourly
  "Returns an hourly forecast for the next 36 hours immediately following
  the api request."
  (api-fn :hourly :hourly_forecast))

(def yesterday "Returns a summary of the observed weather history for yesterday."
  (api-fn :yesterday :history))

(def hourly-seven-day "Returns an hourly forecast for the next 7 days."
  (api-fn :hourly7day :hourly_fourecast))

(def forecast-seven-day
  "Returns a summary of the weather for the next 7 days. This includes high and
   low temperatures, a string text forecast, and the conditions."
  (api-fn :forecast7day :forecast))

;; This one is implemented a bit differently because it requires a date.
(defn history
  "Returns a summary of the observed weather for the specified date, which
   Should be a string of the format YYYYMMDD."
  [key location date]
  (api-call key (str "history_" date) location :history))

