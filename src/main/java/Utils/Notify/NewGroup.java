/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils.Notify;

import Utils.GroupChatInfo;

/**
 *
 * @author dvtuong
 */
public class NewGroup extends Notify {
    public GroupChatInfo info;
    public NewGroup(GroupChatInfo info) {
        this.info = info;
    }
}
