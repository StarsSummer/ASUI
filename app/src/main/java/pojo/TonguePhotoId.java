package pojo;
// Generated 2017-8-29 9:54:56 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * TonguePhotoId generated by hbm2java
 */
public class TonguePhotoId  implements java.io.Serializable {


     private int code;
     private Date uploadDate;

    public TonguePhotoId() {
    }

    public TonguePhotoId(int code, Date uploadDate) {
       this.code = code;
       this.uploadDate = uploadDate;
    }
   
    public int getCode() {
        return this.code;
    }
    
    public void setCode(int code) {
        this.code = code;
    }
    public Date getUploadDate() {
        return this.uploadDate;
    }
    
    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }


   public boolean equals(Object other) {
         if ( (this == other ) ) return true;
		 if ( (other == null ) ) return false;
		 if ( !(other instanceof TonguePhotoId) ) return false;
		 TonguePhotoId castOther = ( TonguePhotoId ) other; 
         
		 return (this.getCode()==castOther.getCode())
 && ( (this.getUploadDate()==castOther.getUploadDate()) || ( this.getUploadDate()!=null && castOther.getUploadDate()!=null && this.getUploadDate().equals(castOther.getUploadDate()) ) );
   }
   
   public int hashCode() {
         int result = 17;
         
         result = 37 * result + this.getCode();
         result = 37 * result + ( getUploadDate() == null ? 0 : this.getUploadDate().hashCode() );
         return result;
   }   


}

