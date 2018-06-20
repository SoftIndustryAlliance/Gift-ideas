package com.soft_industry.findgift.data.datasources.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.soft_industry.findgift.data.dto.gifts.GiftDTO;
import com.soft_industry.findgift.data.dto.gifts.GiftTargetDTO;
import com.soft_industry.findgift.data.dto.gifts.ShopTypeDTO;
import com.soft_industry.findgift.data.dto.gifts.TargetGiftJoin;

import java.util.List;

import io.reactivex.Single;

/**
 * Created by user on 4/20/18.
 */
@Dao
public interface DataDao {
    @Insert
    void insertGiftTargets(List<GiftTargetDTO> dto);
    @Insert
    long insertGift(GiftDTO dto);
    @Insert
    void insertShopTypes(List<ShopTypeDTO> dto);

    @Insert
    void inserttargetGift(TargetGiftJoin targetGiftJoin);

    @Query("SELECT * FROM gift_targets where parent = :id")
    Single<List<GiftTargetDTO>> getGiftTargetsForParent(int id);

    @Query("SELECT gifts.* FROM gifts inner join targets_gift on targets_gift.gift_id = gifts.id where targets_gift.target_id = :id")
    Single<List<GiftDTO>> getGiftsForTarget(Long id);

    @Query("SELECT shop_type.* FROM shop_type INNER JOIN gift_shop_types on shop_type.id=gift_shop_types.shop_type_id where gift_shop_types.gift_id = :giftId")
    Single<List<ShopTypeDTO>> getGiftShopsForGift(Long giftId);

    @Query("SELECT * FROM gift_targets where label in (:list)")
    List<GiftTargetDTO> getTargetsIn(List<String> list);

    @Query("SELECT * FROM gifts WHERE gifts.id = abs(random()) % (SELECT max(gifts.id) FROM gifts) + 1;")
    Single<GiftDTO> getRandomGift();

}
