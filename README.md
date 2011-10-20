# Ororo

A library for talking to the Wunderground API.

It currently implements all of the [Wunderground Weather API](http://www.wunderground.com/weather/api/d/documentation.html), with the exception of autocomplete.

## Usage

Ororo defines a function for each API 'feature'. Each API function (with the exception of `history`) takes two arguments: **key** and **location**.

**key** is an API key for the wunderground API. You can get one of these keys instantly for free by signing up on the [website](http://api.wunderground.com/weather/api). API keys are free for non-commercial and lower-end usage.

**location** is the place you want to get information about. It can be presented in a number of ways. It can be a string of a zip code like `"35554"`, or just a string representation of a place: `"CA/San Francisco"`. Note that spaces are converted to underscores. If you don't want to pass a string, you can pass a collection of city and state or similar: `["Eldridge" "Alabama"]`, `["Sydney" "Australia"]`, etc.

The `history` function takes an extra argument. It is a date of the format `"YYYYMMDD"`.

The API functions usually return massive maps. There is no way in red hot hell that I can document each and every key that the API throws down. You're on your own with that one. Furthermore, `clojure.data.json` doesn't do much of anything to prettify json keys. They are ususally word separated by underscores rather than hyphens. I'm not sure it is worth the computational power to make these keys prettier.

### Examples

*Note that I was stupid enough to commit my real API key below. Rather than bother fixing the repo (by breaking its history), I simply regenerated my key. As a result, the below examples won't work unless you replace my key with one of your own.*

    user=> (use 'ororo.core)
    nil
    user=> (radar "bd7953ef3c00c914" ["Eldridge" "Alabama"])
    {:image_url "http://resize.wunderground.com/cgi-bin/resize_convert?ox=gif&url=radblast/cgi-bin/radar/WUNIDS_composite%3Fcenterlat=33.92213058%26centerlon=-87.62310028%26radius=75%26newmaps=1%26smooth=1%26newmaps=1%26reproj.automerc=1%26api_key=bd7953ef3c00c914", :url "http://www.wunderground.com/radar/radblast.asp?ID=GWX&region=c4&lat=33.92213058&lon=-87.62310028"}
    user=> (radar "bd7953ef3c00c914" "35554")
    {:image_url "http://resize.wunderground.com/cgi-bin/resize_convert?ox=gif&url=radblast/cgi-bin/radar/WUNIDS_composite%3Fcenterlat=33.92213058%26centerlon=-87.62310028%26radius=75%26newmaps=1%26smooth=1%26newmaps=1%26reproj.automerc=1%26api_key=bd7953ef3c00c914", :url "http://www.wunderground.com/radar/radblast.asp?ID=GWX&region=c4&lat=33.92213058&lon=-87.62310028"}
    user=> (satellite "bd7953ef3c00c914" ["Eldridge" "Alabama"])
    {:image_url "http://wublast.wunderground.com/cgi-bin/WUBLAST?lat=33.92213058&lon=-87.62310028&radius=75&width=300&height=300&key=sat_ir4_thumb&gtt=0&extension=png&proj=me&num=1&delay=25&timelabel=0&basemap=1&borders=1&theme=WUNIDS&rand=1318928092&api_key=bd7953ef3c00c914", :image_url_ir4 "http://wublast.wunderground.com/cgi-bin/WUBLAST?lat=33.92213058&lon=-87.62310028&radius=75&width=300&height=300&key=sat_ir4_thumb&gtt=0&extension=png&proj=me&num=1&delay=25&timelabel=0&basemap=1&borders=1&theme=WUNIDS&rand=1318928092&api_key=bd7953ef3c00c914", :image_url_vis "http://wublast.wunderground.com/cgi-bin/WUBLAST?lat=33.92213058&lon=-87.62310028&radius=75&width=300&height=300&key=sat_vis_thumb&gtt=0&extension=png&proj=me&num=1&delay=25&timelabel=0&basemap=1&borders=1&theme=WUNIDS&rand=1318928092&api_key=bd7953ef3c00c914"}


## Installation

In cake or leiningen: `:dependencies [[ororo "0.1.0-alpha1"]]`.
