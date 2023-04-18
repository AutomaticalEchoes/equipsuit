# 装备套装/装备快速切换(EquipSuit)
[![Build Status](https://img.shields.io/badge/MinecraftForge-1.19.x-brightgreen)](https://github.com/MinecraftForge/MinecraftForge?branch=1.19.x)
 
_阅前提示：本人喜欢用<span title="如果影响你观看就先给你道个歉啦！>-<" >`…`</span>来添加注释。_
## 介绍

该模组为方便快速切换装备套装而设计，其中预定义了四个可供快速切换的不同装备套装。
在游戏中，你可以使用 "R" 键快速切换不同装备套装，或者使用 "B" 键打开装备库存以编辑套装设置。

## 使用

安装本模组后，在游戏中你会看到快捷物品栏右侧出现罗马数字 I-IV，表示四种可以快速切换的套装。
当没有特殊需求时，只需切换套装编号并穿上对应装备即可。例如，如果我想设置三号套装为铁套，只需按下 `R` 键切换至 III 套装，然后右键点击装备铁套即可。

### 切换模式

本模组有两种切换模式：升序循环切换和快速选定切换。按下 `Ctrl + R` 可以在两种模式之间切换，同时右下角会有当前模式的提示。

默认情况下，模组使用的是升序循环切换模式，你可以通过按下 `R` 键在四个套装之间进行切换。
使用快速选定切换模式时，则需要按下 `Ctrl + <?,  ?∈[1,4]>`。

当然，这些按键都可以自行修改的。

### 修改映射表

#### 如果我不想整套切换，只需要切换其中某几件装备怎么办？

不用担心，该模组实际上是通过对物品格编号的映射表来实现的，且允许玩家自由编辑该映射表。操作如下：

1. 按下 `B` 键打开相关编辑界面。
2. 点击设置按钮`⚙`，激活部位选择按钮。<span title="H、B、L、F，即头盔、胸甲、护腿、鞋子" >`…`</span>
3. 选择你要编辑的部位，然后单击你想要映射的装备格。
4. 当对应标签标记该格子时，表示映射设置成功。

通过这种方法，你可以将不同套装映射到相同的格子，例如将所有套装的头盔映射至 1 号格。此时切换至任何套装时头盔装备都不会变更。

默认情况下，第一套装备（I）映射到装备库中的 1 到 4 号格，第二组（II）映射到 5 到 8 号格，以此类推。
不同套装的映射格都用了不同颜色的标记（实际上，不同部位的标记图标也有不同的字母，但在窗口模式下分辨率较低时可能无法显示）。

## *注意：*

1. 映射表只能映射至界面左侧装备物品栏中的36个格子，不能映射到原版物品栏中的36个格子。
2. 处于编辑状态时，点击除装备物品栏以外的其他地方将没有正常反馈，你可以再次点击部位选择按钮或者设置按钮取消本次编辑操作。
3. 在编辑映射表时请养成清空装备格的习惯，否则容易出现反逻辑直觉错误。  
   该问题的本质是**穿戴中的装备优先于背包中的装备**，假如你身上穿戴铁头盔，然后将四个套装的头盔映射至含有钻石头盔的格子，
   此时无论你切换到任何套装你的头部装备都是铁头盔而非钻石头盔，
   因为<span title="1. 钻石（物品栏）<-> 铁（穿戴中)&#10;2. 铁（物品栏）<-> 钻石（穿戴中)&#10;结果：钻石（物品栏）， 铁（穿戴中)&#10;（即，将穿戴中的装备脱下到映射格中，再从相同或另一个映射格中获取装备并穿戴至相应部位）">**装备切换过程中一共发生了两次交换`…`**</span>。   
   <span title="其实代码已经写好了，只是界面布局太局促了">2.0版本伴随界面更新~~可能~~会修复这个问题</span>

[使用示例](https://www.bilibili.com/video/BV1Mj411c72Q/)
--------------------------------------------------------
**我是个萌新moder,这是我的第一个模组,望大家多多海涵.  
如果你发现了什么问题或者有什么建议,可以发邮件给我.~~回不回复随缘~~  
email:1121665965@qq.com.**
