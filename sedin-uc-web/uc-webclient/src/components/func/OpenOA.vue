<template>
  <div class="accept-container">
    <!--<div class="go-back" v-show="goBackState" @click="goBack">GoBack</div>-->
    <ul>
      <li v-for="item in webAddress">
        <a :href="item.link" target="showHere" @click="showIframe">{{item.name}}</a>
      </li>
    </ul>
    <iframe v-show="iframeState" id="show-iframe"  frameborder=0 name="showHere" scrolling=auto src=""></iframe>
  </div>
</template>

<script>
	export default {
		data() {
			return {
			  iframeState:false,
        goBackState:false,
        webAddress: [
          {
            name:'npm & nodejs',
            link:'http://www.runoob.com/nodejs/nodejs-npm.html'
          },
          {
            name:'vue',
            link:'https://cn.vuejs.org/'
          },
          {
            name:'element ui',
            link:'http://element.eleme.io/#/zh-CN'
          }
        ]
			}
		},
    mounted(){
      const oIframe = document.getElementById('show-iframe');
      const deviceWidth = document.documentElement.clientWidth;
      const deviceHeight = document.documentElement.clientHeight;
      oIframe.style.width = deviceWidth + 'px';
      oIframe.style.height = deviceHeight + 'px';
    },
    methods:{
      goBack(){
        this.goBackState = false;
        this.iframeState = false;
      },
      showIframe(){
        this.goBackState = true;
        this.iframeState = true;
      }
    }
	}

</script>
