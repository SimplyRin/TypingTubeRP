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
 * Copyright (c) 2019 SimplyRin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
