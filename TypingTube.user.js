// ==UserScript==
// @name         Typing Tube
// @namespace    https://www.simplyrin.net/
// @version      0.1
// @description  Typing Tube でプレイ中の音楽を Discord Rich Presence に表示します。
// @author       SimplyRin
// @match        https://typing-tube.net/*
// @require      https://cdnjs.cloudflare.com/ajax/libs/jquery/3.4.1/jquery.min.js
// ==/UserScript==

/**
 * Created by SimplyRin on 2020/02/09.
 *
 * Copyright (c) 2020 SimplyRin
 *
 * Eclipse Public License - v 2.0
 */
var movieTitle = document.getElementsByClassName("movietitle")[0];
if (movieTitle == undefined) {
    setTimeout(function() {
        $.getJSON("http://127.0.0.1:8843/post?lobby", function(data) {
        });
    });
    return;
}
var innerHTML = movieTitle.innerHTML.replace(/<\/?[^>]+(>|$)/g, "").trim();

var title = innerHTML.split("(難易度")[0].trim().replace("　", " ");
var level = innerHTML.split("(難易度")[1].split(")")[0].toLowerCase().replace("lv", "");
var url = location.href;

var json = '{"title":"' + title + '","level":' + level + ',"url":"' + url + '"}'

// Connecting
setTimeout(function() {
    $.getJSON("http://127.0.0.1:8843/post?" + json, function(data) {
    });
});
