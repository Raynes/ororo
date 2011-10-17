# Ororo

A library for talking to the Wunderground API.

It currently implements all of the [Wunderground Weather API](http://www.wunderground.com/weather/api/d/documentation.html), with the exception of autocomplete.

## Usage

Ororo defines a function for each API 'feature'. Each API function (with the exception of `history`) takes two arguments: **key** and **location**.

**key** is an API key for the wunderground API. You can get one of these keys instantly for free by signing up on the [website](http://api.wunderground.com/weather/api). API keys are free for non-commercial and lower-end usage.

**location** is the place you want to get information about. It can be presented in a number of ways. It can be a string of a zip code like `"35554"`, or just a string representation of a place: `"CA/San Francisco"`. Note that spaces are converted to underscores. If you don't want to pass a string, you can pass a collection of city and state or similar: `["Eldridge" "Alabama"]`, `["Sydney" "Australia"]`, etc.

The `history` function takes an extra argument. It is a date of the format `"YYYYMMDD"`.

The API functions usually return massive maps. There is no way in red hot hell that I can document each and every key that the API throws down. You're on your own with that one. Furthermore, `clojure.data.json` doesn't do much of anything to prettify json keys. They are ususally word separated by underscores rather than hyphens. I'm not sure it is worth the computational power to make these keys prettier.

## Installation

In cake or leiningen: `:dependencies [[ororo "0.1.0-alpha1"]]`.
