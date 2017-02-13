# App Base: Prender
[![Build Status](https://travis-ci.org/JFL110/app-base-prender.svg?branch=master)](https://travis-ci.org/JFL110/app-base-prender)
[![Coverage Status](https://coveralls.io/repos/github/JFL110/app-base-prender/badge.svg?branch=master)](https://coveralls.io/github/JFL110/app-base-prender?branch=master)

See [examples](https://github.com/JFL110/app-base-examples)

## Parse
Stage one is parsing. HTML and other content is read and transformed to a tree structure of _RenderNodes_ that can represent either layout elements, such as a HTML tag, or dyanmic elements, such as a users profile picture. This model is then cached and used across requests.

#### Inlining
Mark _script_ or _link_ tags with an _inline_ attribute to pull the content into the same page and reduce client requests for small resources. 

#### Compression
All CSS and JS will be smooshed using the [YUI compressor](http://yui.github.io/yuicompressor/).

#### Placeholders
Any tag with an _data-placeholder-key_ attribute or _x:placeholder_ with a _key_ attribute will be replaced with a placeholder node. These can then be swapper with dynamic values on a per request basis.

#### Anything else
Create and bind any _ParseTransformation_ to replace HTML tags with other nodes.

## Render
...
